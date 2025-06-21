package com.inventory.dto;

import com.inventory.models.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long productId;
    @NotEmpty(message = "product name required")
    private String productName;
    @NotEmpty(message = "product description required")
    private String description;
    @NotNull(message = "product price required")
    private Double price;
    @NotNull(message = "stock quantity required")
    @Min(1)
    @Max(200)
    private Long stockQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
