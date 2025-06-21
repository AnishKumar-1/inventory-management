package com.inventory.repository;

import com.inventory.models.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryRepo extends JpaRepository<StockHistory,Long> {
}
