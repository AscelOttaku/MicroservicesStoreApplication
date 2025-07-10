package kg.com.orderapplication.service.impl;

import kg.com.orderapplication.dto.InventoryItems;
import kg.com.orderapplication.dto.InventoryResponse;
import kg.com.orderapplication.dto.OrderDto;
import kg.com.orderapplication.dto.OrderOperationResultDto;
import kg.com.orderapplication.mapper.OrderMapper;
import kg.com.orderapplication.model.Order;
import kg.com.orderapplication.model.OrderLineItems;
import kg.com.orderapplication.repository.OrderRepository;
import kg.com.orderapplication.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestClient restClient;

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    @Override
    public void placeOrder(OrderDto orderDto) {
        Order order = orderMapper.mapToModel(orderDto);
        order.setOrderNumber(UUID.randomUUID().toString());
        order.getOrderLineItems().forEach(orderLineItem -> orderLineItem.setOrder(order));

        var inventoryResponses = restClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8082)
                        .path("api/inventory/products/fetch/from-stock")
                        .build())
                .body(orderDto.getOrderLineItems().stream()
                        .map(orderLineItemsDto -> InventoryItems.builder()
                                .skuCode(orderLineItemsDto.getSkuCode())
                                .quantity(orderLineItemsDto.getQuantity())
                                .build()))
                .retrieve()
                .body(new ParameterizedTypeReference<List<InventoryResponse>>() {
                });

        if (inventoryResponses == null || inventoryResponses.isEmpty())
            throw new IllegalArgumentException("No inventory information found for the provided SKU codes.");

        inventoryResponses.forEach(inventoryResponse -> {
            if (!inventoryResponse.isFetchedFromStock()) {
                sendOperationFailed(inventoryResponse);
                throw new IllegalArgumentException("Product with SKU code " + inventoryResponse.getSkuCode() + " is out of stock.");
            }
        });

        var savedOrder = orderRepository.save(order);
        sendOperationSucceeded(savedOrder);
    }

    private void sendOperationFailed(InventoryResponse inventoryResponse) {
        restClient.put()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8082)
                        .path("api/inventory/order/operation-result")
                        .build())
                .body(OrderOperationResultDto.builder()
                        .message("Product with SKU code " + inventoryResponse.getSkuCode() + " is out of stock.")
                        .operationSucceeded(false)
                        .build())
                .retrieve()
                .toBodilessEntity();
    }

    private void sendOperationSucceeded(Order order) {
        restClient.put()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8082)
                        .path("api/inventory/order/operation-result")
                        .build())
                .body(OrderOperationResultDto.builder()
                        .message("Order with ID " + order.getId() + " has been successfully placed.")
                        .operationSucceeded(true)
                        .build())
                .retrieve()
                .toBodilessEntity();
    }
}
