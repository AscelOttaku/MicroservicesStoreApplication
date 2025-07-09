package kg.com.productapplication.service;

import kg.com.productapplication.dto.request.ProductRequestDto;
import kg.com.productapplication.dto.response.PageHolder;
import kg.com.productapplication.dto.response.ProductResponseDto;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    PageHolder<ProductResponseDto> findAllProducts(int page, int size);
}
