package kg.com.orderapplication.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderLineItemsDto {
    private Long id;

    @NotBlank(message = "SKU code must not be blank")
    @Size(max = 255, message = "SKU code must not exceed 255 characters")
    private String skuCode;

    @NotNull(message = "Price is required")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid amount with up to 2 decimal places")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}