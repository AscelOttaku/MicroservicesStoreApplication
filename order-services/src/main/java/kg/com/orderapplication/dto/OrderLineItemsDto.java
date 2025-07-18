package kg.com.orderapplication.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderLineItemsDto {
    private Long id;

    @NotBlank(message = "SKU code must not be blank")
    @Size(max = 255, message = "SKU code must not exceed 255 characters")
    private String skuCode;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}