package kg.com.productapplication.controller;

import jakarta.validation.Valid;
import kg.com.productapplication.dto.request.ProductRequestDto;
import kg.com.productapplication.dto.response.PageHolder;
import kg.com.productapplication.dto.response.ProductResponseDto;
import kg.com.productapplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        productService.createProduct(productRequestDto);
    }

    @GetMapping("all")
    @ResponseStatus(HttpStatus.OK)
    public PageHolder<ProductResponseDto> findALlProducts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return productService.findAllProducts(page, size);
    }
}

