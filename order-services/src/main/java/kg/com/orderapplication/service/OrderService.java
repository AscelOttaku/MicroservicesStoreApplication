package kg.com.orderapplication.service;

import kg.com.orderapplication.dto.OrderDto;

public interface OrderService {
    void placeOrder(OrderDto orderDto);
}
