package kg.com.inventoryapplication.repository;

import kg.com.inventoryapplication.dto.InventoryResponse;
import kg.com.inventoryapplication.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("select count(i) > 0 from Inventory i where i.skuCode = :skuCode")
    boolean existsBySkuCode(String skuCode);

    @Query("select count(i) > 0 from Inventory i where i.skuCode = :skuCode and i.quantity > 0")
    boolean isProductInStock(String skuCode);

    @Query("select count(i) > 0 from Inventory i where i.skuCode in :skuCodes and i.quantity > 0")
    boolean existsInStockBySkuCode(List<String> skuCodes);

    @Query("select new kg.com.inventoryapplication.dto.InventoryResponse(i.skuCode, i.quantity) " +
           "from Inventory i where i.skuCode in :skuCodes")
    Page<InventoryResponse> findAllBySkuCodeIn(List<String> skuCodes, Pageable pageable);
}
