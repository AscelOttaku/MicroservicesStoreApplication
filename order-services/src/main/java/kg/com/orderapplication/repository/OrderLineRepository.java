package kg.com.orderapplication.repository;

import kg.com.orderapplication.model.OrderLineItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLineItems, Long> {
}
