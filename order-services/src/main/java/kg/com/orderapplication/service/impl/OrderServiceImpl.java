package kg.com.orderapplication.service.impl;

import kg.com.orderapplication.dto.InventoryResponse;
import kg.com.orderapplication.dto.OrderDto;
import kg.com.orderapplication.dto.PageHolder;
import kg.com.orderapplication.mapper.OrderMapper;
import kg.com.orderapplication.model.Order;
import kg.com.orderapplication.model.OrderLineItems;
import kg.com.orderapplication.repository.OrderRepository;
import kg.com.orderapplication.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestClient restClient;

    @Override
    public void placeOrder(OrderDto orderDto) {
        Order order = orderMapper.mapToModel(orderDto);
        order.setOrderNumber(UUID.randomUUID().toString());
        order.getOrderLineItems().forEach(orderLineItem -> orderLineItem.setOrder(order));

        var inventoryResponses = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8082)
                        .path("api/inventory/products/exists")
                        .queryParam("skuCodes", order.getOrderLineItems().stream()
                                .map(OrderLineItems::getSkuCode)
                                .toList())
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<PageHolder<InventoryResponse>>() {
                });

        if (inventoryResponses == null || inventoryResponses.getContent() == null ||
                inventoryResponses.getContent().isEmpty())
            throw new IllegalArgumentException("No inventory information found for the provided SKU codes.");

        inventoryResponses.getContent().forEach(inventoryResponse -> {
            if (inventoryResponse.getQuantity() <= 0) {
                throw new IllegalArgumentException("Product with SKU code " + inventoryResponse.getSkuCode() + " is out of stock.");
            }
        });

        orderRepository.save(order);
    }
}
