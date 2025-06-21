package com.inventory.dto;

import com.inventory.models.Product;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Long category_id;
    @NotEmpty(message="category name not found")
    private String categoryName;
    List<ProductDto> products;

}
