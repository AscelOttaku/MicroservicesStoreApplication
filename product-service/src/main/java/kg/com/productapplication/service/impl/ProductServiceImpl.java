package kg.com.productapplication.service.impl;

import kg.com.productapplication.dto.mapper.PageHolderWrapper;
import kg.com.productapplication.dto.mapper.ProductMapper;
import kg.com.productapplication.dto.request.ProductRequestDto;
import kg.com.productapplication.dto.response.PageHolder;
import kg.com.productapplication.dto.response.ProductResponseDto;
import kg.com.productapplication.model.Product;
import kg.com.productapplication.repository.ProductRepository;
import kg.com.productapplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final PageHolderWrapper pageHolderWrapper;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = productMapper.mapToModel(productRequestDto);
        return productMapper.mapToDto(productRepository.save(product));
    }

    @Override
    public PageHolder<ProductResponseDto> findAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        var productPage = productRepository.findAll(pageable)
                .map(productMapper::mapToDto);
        return pageHolderWrapper.wrapPageHolder(productPage);
    }
}
