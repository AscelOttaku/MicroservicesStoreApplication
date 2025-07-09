package kg.com.productapplication.dto.mapper;

import kg.com.productapplication.dto.request.ProductRequestDto;
import kg.com.productapplication.dto.response.ProductResponseDto;
import kg.com.productapplication.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDto mapToDto(Product product);
    Product mapToProduct(ProductResponseDto productResponseDto);

    ProductRequestDto mapToProductDto(Product product);
    Product mapToModel(ProductRequestDto productRequestDto);
}
