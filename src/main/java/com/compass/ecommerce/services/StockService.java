package com.compass.ecommerce.services;

import com.compass.ecommerce.domain.Product;
import com.compass.ecommerce.domain.Stock;
import com.compass.ecommerce.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public void addStock(Long stockId, Integer quantity){
        stockRepository.addStock(stockId, quantity);
    }
}
