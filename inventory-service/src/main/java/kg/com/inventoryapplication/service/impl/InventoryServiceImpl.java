package kg.com.inventoryapplication.service.impl;

import kg.com.inventoryapplication.dto.InventoryItems;
import kg.com.inventoryapplication.dto.InventoryResponse;
import kg.com.inventoryapplication.dto.OrderOperationResultDto;
import kg.com.inventoryapplication.dto.PageHolder;
import kg.com.inventoryapplication.dto.mapper.PageHolderWrapper;
import kg.com.inventoryapplication.model.Inventory;
import kg.com.inventoryapplication.repository.InventoryRepository;
import kg.com.inventoryapplication.service.InventoryService;
import kg.com.inventoryapplication.storage.TemporalStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final PageHolderWrapper pageHolderWrapper;
    private final TemporalStorage temporalStorage;

    @Override
    public boolean isInventoryExistBySkuCode(String skuCode) {
        return inventoryRepository.existsBySkuCode(skuCode);
    }

    @Override
    public boolean isProductsInStockBySkuCode(String skuCode) {
        return inventoryRepository.isProductInStock(skuCode);
    }

    @Override
    public boolean isProductsExistsInStockBySkuCode(List<String> skuCodes) {
        return inventoryRepository.existsInStockBySkuCode(skuCodes);
    }

    @Override
    public PageHolder<InventoryItems> fetchFromStock(List<String> skuCodes, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("quantity").descending());
        return pageHolderWrapper.wrapPageHolder(inventoryRepository.findAllBySkuCodeIn(skuCodes, pageable));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    @Override
    public List<InventoryResponse> fetchFromStock(List<InventoryItems> skuCodes) {
        Map<String, Integer> requestedQtyMap = skuCodes.stream()
                .collect(Collectors.toMap(InventoryItems::getSkuCode, InventoryItems::getQuantity));

        List<Inventory> inventories = inventoryRepository.findAllBySkuCodeIn(requestedQtyMap.keySet().stream().toList());

        List<InventoryResponse> responses = new ArrayList<>();

        for (Inventory inventory : inventories) {
            String sku = inventory.getSkuCode();
            int availableQuantity = inventory.getQuantity();
            int requestedQuantity = requestedQtyMap.getOrDefault(sku, 0);

            boolean canFetch = availableQuantity >= requestedQuantity;
            int quantity = Math.max(availableQuantity - requestedQuantity, 0);
            if (canFetch)
                inventoryRepository.updateQuantityBySkuCodeIn(sku, requestedQuantity);

            responses.add(InventoryResponse.builder()
                    .skuCode(sku)
                    .quantityLeftInStock(quantity)
                    .requestedQuantity(requestedQuantity)
                    .isFetchedFromStock(canFetch)
                    .build());
        }
        temporalStorage.addData("fetchedInventoryItems", responses);
        return responses;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    @Override
    public void handleOperationResult(OrderOperationResultDto orderOperationResultDto) {
        if (orderOperationResultDto.isOperationSucceeded())
            return;

        var inventoryItems = temporalStorage.getTemporalData("fetchedInventoryItems", new ParameterizedTypeReference<ArrayList<InventoryResponse>>() {
        });
        for (InventoryResponse inventoryResponse : inventoryItems) {
            if (!inventoryResponse.isFetchedFromStock())
                continue;

            Inventory inventory = inventoryRepository.findBySkuCode(inventoryResponse.getSkuCode())
                    .orElseThrow(() -> new NoSuchElementException("Inventory not found for SKU: " + inventoryResponse.getSkuCode()));

            inventory.setQuantity(inventoryResponse.getRequestedQuantity() + inventory.getQuantity());
            inventoryRepository.save(inventory);
        }
    }
}
