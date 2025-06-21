package com.inventory.services;

import com.inventory.dto.StockHistoryDto;
import com.inventory.exceptions.DataNotFound;
import com.inventory.mapper.CommonObjectMapper;
import com.inventory.models.Product;
import com.inventory.models.StockHistory;
import com.inventory.repository.ProductRepo;
import com.inventory.repository.StockHistoryRepo;
import com.inventory.utils.ChangeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class StockHistoryService {

    @Autowired
    private StockHistoryRepo stockHistoryRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CommonObjectMapper objectMapper;

    // CREATE stock history and update product stock
    public ResponseEntity<StockHistoryDto> create(StockHistoryDto stockHistoryDto, Long product_id) {
        Product product = productRepo.findById(product_id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + product_id));

        StockHistory stockHistory = objectMapper.convert(stockHistoryDto, StockHistory.class);
        stockHistory.setProduct(product);

        // Update stock quantity
        ChangeType type = stockHistory.getChangeType();
        int qtyChange = stockHistory.getQuantityChanged();

        if (type == ChangeType.ADD) {
            product.setStockQuantity(product.getStockQuantity() + qtyChange);
        } else if (type == ChangeType.REMOVE || type == ChangeType.SOLD) {
            long updatedQty = product.getStockQuantity() - qtyChange;
            if (updatedQty < 0) {
                throw new IllegalStateException("Stock cannot be negative");
            }
            product.setStockQuantity(updatedQty);
        }

        // Save changes
        productRepo.save(product);
        StockHistory saved = stockHistoryRepo.save(stockHistory);
        StockHistoryDto responseDto = objectMapper.convert(saved, StockHistoryDto.class);
        return ResponseEntity.ok(responseDto);
    }

    // GET stock history by ID
    public ResponseEntity<StockHistoryDto> stockById(Long stock_id) {
        Optional<StockHistory> stock = stockHistoryRepo.findById(stock_id);
        if (stock.isEmpty()) {
            throw new DataNotFound("Stock history not found with ID: " + stock_id);
        }
        StockHistoryDto response = objectMapper.convert(stock.get(), StockHistoryDto.class);
        return ResponseEntity.ok(response);
    }
}
