package kg.com.orderapplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    @SequenceGenerator(sequenceName = "order_id_seq", name = "order_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "ORDER_NUMBER", nullable = false, length = 255)
    private String orderNumber;

    @Column(name = "ORDER_DATE", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "ORDER_UPDATED_DATE", nullable = false)
    private LocalDateTime orderUpdatedDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLineItems> orderLineItems;

    @PrePersist
    public void prePersist() {
        orderDate = LocalDateTime.now();
        orderUpdatedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        orderUpdatedDate = LocalDateTime.now();
    }
}
