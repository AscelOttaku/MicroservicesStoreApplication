package kg.com.productapplication.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
