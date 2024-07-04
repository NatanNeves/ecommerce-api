package com.compass.ecommerce.repositories;


import com.compass.ecommerce.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Modifying
    @Transactional
    @Query("update Stock s set s.quantity = s.quantity + :quantity where s.id = :stockId")
    void addStock(Long stockId, Integer quantity);
}
