package kg.com.inventoryapplication.service;

import kg.com.inventoryapplication.dto.InventoryItems;
import kg.com.inventoryapplication.dto.InventoryResponse;
import kg.com.inventoryapplication.dto.OrderOperationResultDto;
import kg.com.inventoryapplication.dto.PageHolder;

import java.util.List;

public interface InventoryService {
    boolean isInventoryExistBySkuCode(String skuCode);

    boolean isProductsInStockBySkuCode(String skuCode);

    boolean isProductsExistsInStockBySkuCode(List<String> skuCodes);

    PageHolder<InventoryItems> fetchFromStock(List<String> skuCodes, int page, int size);

    List<InventoryResponse> fetchFromStock(List<InventoryItems> skuCodes);

    void handleOperationResult(OrderOperationResultDto orderOperationResultDto);
}
