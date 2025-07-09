package kg.com.orderapplication.controller;

import jakarta.validation.Valid;
import kg.com.orderapplication.dto.OrderDto;
import kg.com.orderapplication.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void placeOrder(@RequestBody @Valid OrderDto orderDto) {
        orderService.placeOrder(orderDto);
    }
}
