package kg.com.inventoryapplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "INVENTORY")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_seq")
    @SequenceGenerator(name = "inventory_seq", sequenceName = "inventory_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "sku_code", nullable = false)
    private String skuCode;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}