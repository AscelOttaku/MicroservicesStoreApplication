package kg.com.productapplication.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequestDto {

    @NotBlank(message = "Product name must not be blank")
    @Size(max = 255, message = "Product name must not exceed 255 characters")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid monetary amount with up to 10 digits and 2 decimals")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
    private BigDecimal price;
}