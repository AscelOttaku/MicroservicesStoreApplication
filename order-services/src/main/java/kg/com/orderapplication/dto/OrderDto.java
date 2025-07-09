package kg.com.orderapplication.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderDto {
    private Long id;

    @Null(message = "orderNumber must be null")
    private String orderNumber;

    @NotNull(message = "Order line items must not be null")
    @Size(min = 1, message = "At least one order line item is required")
    private List<@Valid OrderLineItemsDto> orderLineItems;
}