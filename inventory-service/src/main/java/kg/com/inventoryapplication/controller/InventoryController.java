package kg.com.inventoryapplication.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import kg.com.inventoryapplication.dto.InventoryResponse;
import kg.com.inventoryapplication.dto.PageHolder;
import kg.com.inventoryapplication.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("exists/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInventoryExists(@PathVariable("sku-code") String skuCode) {
        return inventoryService.isInventoryExistBySkuCode(skuCode);
    }

    @GetMapping("product/exists/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isProductExistInStockBySkuCode(@PathVariable("sku-code") String skuCode) {
        return inventoryService.isProductsInStockBySkuCode(skuCode);
    }

    @GetMapping("products/exists")
    @ResponseStatus(HttpStatus.OK)
    public PageHolder<InventoryResponse> findProductAmountBySkuCode(
            @RequestParam @NotEmpty List<@NotBlank String> skuCodes,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {
        return inventoryService.findProductAmountBySkuCode(skuCodes, page, size);
    }
}
