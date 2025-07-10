package kg.com.inventoryapplication.repository;

import jakarta.validation.constraints.NotBlank;
import kg.com.inventoryapplication.dto.InventoryItems;
import kg.com.inventoryapplication.dto.InventoryResponse;
import kg.com.inventoryapplication.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("select count(i) > 0 from Inventory i where i.skuCode = :skuCode")
    boolean existsBySkuCode(String skuCode);

    @Query("select count(i) > 0 from Inventory i where i.skuCode = :skuCode and i.quantity > 0")
    boolean isProductInStock(String skuCode);

    @Query("select count(i) > 0 from Inventory i where i.skuCode in :skuCodes and i.quantity > 0")
    boolean existsInStockBySkuCode(List<String> skuCodes);

    @Query("select new kg.com.inventoryapplication.dto.InventoryItems(i.skuCode, i.quantity) " +
           "from Inventory i where i.skuCode in :skuCodes")
    Page<InventoryItems> findAllBySkuCodeIn(List<String> skuCodes, Pageable pageable);

    @Query("select i from Inventory i where i.skuCode in :skuCodes")
    List<Inventory> findAllBySkuCodeIn(List<String> skuCodes);

    @Modifying
    @Query("update Inventory i set i.quantity = i.quantity - :quantity where i.skuCode = :skuCode and i.quantity >= :quantity")
    void updateQuantityBySkuCodeIn(String skuCode, int quantity);

    Optional<Inventory> findBySkuCode(String skuCode);
}
