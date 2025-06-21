package com.inventory.controllers;

import com.inventory.dto.StockHistoryDto;
import com.inventory.services.StockHistoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock/history")
@Validated
@CrossOrigin(origins = "*")
public class StockHistoryController {

    @Autowired
    private StockHistoryService stockHistoryService;

    @PostMapping("/{product_id}")
    public ResponseEntity<StockHistoryDto> create(@Valid @RequestBody StockHistoryDto stockHistoryDto
    , @NotNull(message = "product id required") @PathVariable Long product_id){
        return stockHistoryService.create(stockHistoryDto,product_id);
    }

    //get stock by stock id
    @GetMapping("/{stock_id}")
    public ResponseEntity<StockHistoryDto> stock(@NotNull(message = "stock id required") @PathVariable Long stock_id){
        return stockHistoryService.stockById(stock_id);
    }
}
