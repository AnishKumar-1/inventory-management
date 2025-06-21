package com.inventory.utils;

import com.inventory.dto.ProductDto;
import com.inventory.models.Product;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    public ProductDto productDto(Product existsProduct){
        return ProductDto.builder()
                .productId(existsProduct.getProductId())
                .productName(existsProduct.getProductName()).description(existsProduct.getDescription())
                .price(existsProduct.getPrice())
                .stockQuantity(existsProduct.getStockQuantity())
                .createdAt(existsProduct.getCreatedAt()).updatedAt(existsProduct.getUpdatedAt()).build();
    }

    public Product product(ProductDto productDto){
        return Product.builder()
                .productName(productDto.getProductName()).description(productDto.getDescription())
                .price(productDto.getPrice())
                .stockQuantity(productDto.getStockQuantity())
                .createdAt(productDto.getCreatedAt()).updatedAt(productDto.getUpdatedAt()).build();
    }
}
