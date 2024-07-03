package com.compass.ecommerce.services;

import com.compass.ecommerce.dtos.ProductDTO;
import com.compass.ecommerce.domain.Product;
import com.compass.ecommerce.repositories.ProductRepository;
import com.compass.ecommerce.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;


    public Optional<Product> findByName(String name){
        return productRepository.findByName(name);
    }

    public Product createProduct(ProductDTO data) throws Exception {
        if (productRepository.findByName(data.name()).isEmpty()) {
            this.priceValidate(data.price());

            Product prod = new Product();
            prod.setName(data.name());
            prod.setDescription(data.description());
            prod.setPrice(data.price());
            prod.setQuantity(data.quantity());
            return productRepository.save(prod);

        } else {
            throw new Exception("este produto já existe");
        }
    }


    public Product updateProduct(Long id, ProductDTO data) throws Exception {
        Optional<Product> existingProductOpt = productRepository.findById(id);
        this.existingProductValidate(id);

        Product existingProduct = existingProductOpt.get();
        existingProduct.setName(data.name());
        existingProduct.setDescription(data.description());
        existingProduct.setPrice(data.price());
        existingProduct.setQuantity(data.quantity());

        this.priceValidate(data.price());
        return productRepository.save(existingProduct);
    }

    public void priceValidate(Double price) throws Exception {
        if (price == null || price <= 0) {
            throw new Exception("O preço deve ser positivo e não pode ser nulo");
        }
    }

    public void deleteProduct(Long id) throws Exception {
        Optional<Product> existingProductOpt = productRepository.findById(id);
        this.existingProductValidate(id);

        Product existingProduct = existingProductOpt.get();
        productRepository.delete(existingProduct);
    }

    public Product existingProductValidate(Long id) throws Exception {
        Optional<Product> existingProductOpt = productRepository.findById(id);

        if (existingProductOpt.isEmpty()) {
            throw new Exception("ID de produto inválido");
        }
        return existingProductOpt.get();
    }
}
