package com.inventory.dto;

import com.inventory.models.Product;
import com.inventory.utils.ChangeType;
import jakarta.persistence.*;
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
public class StockHistoryDto {

    private Long id;
    @NotNull(message = "Quantity change is required")
    private Integer quantityChanged;
    @NotNull(message = "Change type is required")
    private ChangeType changeType;
    private LocalDateTime timestamp;
    @NotEmpty(message = "Remarks cannot be empty")
    private String remarks;
}

