package com.compass.ecommerce.repositories;

import com.compass.ecommerce.domain.Item;
import com.compass.ecommerce.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByProduct(Product product);
}
