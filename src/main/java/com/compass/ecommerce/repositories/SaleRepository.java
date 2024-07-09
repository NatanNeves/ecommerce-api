package com.compass.ecommerce.repositories;

import com.compass.ecommerce.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByTimestampBetween(Instant startDate, Instant endDate);
}
