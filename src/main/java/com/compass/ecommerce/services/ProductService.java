package com.compass.ecommerce.services;


import com.compass.ecommerce.domain.Stock;
import com.compass.ecommerce.dtos.ProductDTO;
import com.compass.ecommerce.domain.Product;
import com.compass.ecommerce.repositories.ProductRepository;
import com.compass.ecommerce.repositories.StockRepository;
import com.compass.ecommerce.services.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;


    public List<Product> allProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> findByName(String name){
        return productRepository.findByName(name);
    }

    public Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));
    }

    public Product createProduct(ProductDTO productDTO) throws Exception {
        if (productRepository.findByName(productDTO.name()).isEmpty()) {
            priceValidate(productDTO.price());

            Product newProduct = new Product();
            newProduct.setName(productDTO.name());
            newProduct.setDescription(productDTO.description());
            newProduct.setPrice(productDTO.price());
            newProduct.setQuantity(productDTO.quantity());
            newProduct.setAvailable(true);

            Stock stock = new Stock();
            stock.setQuantity(productDTO.quantity());
            stock = stockRepository.save(stock);
            newProduct.setStock(stock);

            Product savedProduct = productRepository.save(newProduct);
            stockRepository.addStock(stock.getId(), productDTO.quantity());
            return savedProduct;
        } else {
            throw new Exception("Este produto já existe");
        }
    }

    public Product updateProduct(Long id, ProductDTO data) throws Exception {
        existingProductValidate(id);

        Optional<Product> existingProductOpt = productRepository.findById(id);
        if (existingProductOpt.isEmpty()) {
            throw new Exception("Produto não encontrado com o ID fornecido: " + id);
        }

        Product existingProduct = existingProductOpt.get();
        existingProduct.setName(data.name());
        existingProduct.setDescription(data.description());
        existingProduct.setPrice(data.price());
        existingProduct.setQuantity(data.quantity());

        priceValidate(data.price());
        return productRepository.save(existingProduct);
    }

    public void priceValidate(Double price) throws Exception {
        if (price == null || price <= 0) {
            throw new Exception("O preço deve ser positivo e não pode ser nulo");
        }
    }

    public void deleteProduct(Long id) throws Exception {
        Product existingProduct = existingProductValidate(id);
        stockRepository.delete(existingProduct.getStock());
        productRepository.delete(existingProduct);
    }

    private Product existingProductValidate(Long id) throws Exception {
        Optional<Product> existingProductOpt = productRepository.findById(id);

        if (existingProductOpt.isEmpty()) {
            throw new Exception("Produto não encontrado com o ID: " + id);
        }

        return existingProductOpt.get();
    }
}
