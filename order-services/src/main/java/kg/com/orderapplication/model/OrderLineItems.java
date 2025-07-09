package kg.com.orderapplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "order_line_items")
public class OrderLineItems {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_line_items_id_seq")
    @SequenceGenerator(sequenceName = "order_line_items_id_seq", name = "order_line_items_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "sku_code", nullable = false, length = 255)
    private String skuCode;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id")
    private Order order;
}
