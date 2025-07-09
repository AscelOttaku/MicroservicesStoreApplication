package kg.com.inventoryapplication.service;

import kg.com.inventoryapplication.dto.InventoryResponse;
import kg.com.inventoryapplication.dto.PageHolder;

import java.util.List;

public interface InventoryService {
    boolean isInventoryExistBySkuCode(String skuCode);

    boolean isProductsInStockBySkuCode(String skuCode);

    boolean isProductsExistsInStockBySkuCode(List<String> skuCodes);

    PageHolder<InventoryResponse> findProductAmountBySkuCode(List<String> skuCodes, int page, int size);
}
