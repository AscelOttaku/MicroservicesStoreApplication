package kg.com.inventoryapplication.service.impl;

import kg.com.inventoryapplication.dto.InventoryResponse;
import kg.com.inventoryapplication.dto.PageHolder;
import kg.com.inventoryapplication.dto.mapper.PageHolderWrapper;
import kg.com.inventoryapplication.repository.InventoryRepository;
import kg.com.inventoryapplication.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final PageHolderWrapper pageHolderWrapper;

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
    public PageHolder<InventoryResponse> findProductAmountBySkuCode(List<String> skuCodes, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("quantity").descending());
        return pageHolderWrapper.wrapPageHolder(inventoryRepository.findAllBySkuCodeIn(skuCodes, pageable));
    }
}
