package com.compass.ecommerce.services;

import com.compass.ecommerce.dtos.ProductDTO;
import com.compass.ecommerce.domain.Product;
import com.compass.ecommerce.repositories.ProductRepository;
import com.compass.ecommerce.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;


    public Product createProduct(ProductDTO data){
       //fazer tratamento de possiveis excessões

        Product prod = new Product();
        prod.setName(data.name());
        prod.setDescription(data.description());
        prod.setPrice(data.price());
        prod.setQuantity(data.quantity());

        return prod;
    }
}
