package com.compass.ecommerce.services;


import com.compass.ecommerce.domain.Item;
import com.compass.ecommerce.domain.Stock;
import com.compass.ecommerce.dtos.ProductDTO;
import com.compass.ecommerce.domain.Product;
import com.compass.ecommerce.repositories.ItemRepository;
import com.compass.ecommerce.repositories.ProductRepository;
import com.compass.ecommerce.repositories.StockRepository;
import com.compass.ecommerce.services.exceptions.*;
import io.micrometer.common.util.StringUtils;
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

    @Autowired
    private ItemRepository itemRepository;


    public List<Product> allProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> findByName(String name){
        return productRepository.findByName(name);
    }

    public Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new NotFoundException("Produto não encontrado"));
    }

    public Product createProduct(ProductDTO productDTO){
        emptyFieldValidate(productDTO);
        priceValidate(productDTO.price());
        quantityValidate(productDTO.quantity());

        if (productRepository.findByName(productDTO.name()).isPresent()) {
            throw new ExistingObjectException("Este produto já existe");
        }

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
    }

    public Product updateProduct(Long id, ProductDTO data){
        emptyFieldValidate(data);

        existingProductValidate(id);
        Optional<Product> existingProductOpt = productRepository.findById(id);
        if (existingProductOpt.isEmpty()) {
            throw new NotFoundException("Produto não encontrado");
        }

        Product existingProduct = existingProductOpt.get();
        existingProduct.setName(data.name());
        existingProduct.setDescription(data.description());
        existingProduct.setPrice(data.price());
        existingProduct.setQuantity(data.quantity());
        quantityValidate(data.quantity());
        priceValidate(data.price());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        Product existingProduct = existingProductValidate(id);

        // Busca por itens de venda que contenham o produto
        List<Item> itemsWithProduct = itemRepository.findByProduct(existingProduct);

        if (!itemsWithProduct.isEmpty()) {
            // Se houver itens de venda com o produto, marca o produto como indisponível
            existingProduct.setAvailable(false);
            productRepository.save(existingProduct);
        } else {
            // Se não houver itens de venda com o produto, pode ser deletado
            stockRepository.delete(existingProduct.getStock());
            productRepository.delete(existingProduct);
        }
    }

    private void priceValidate(Double value) {
        if (value == null || value < 0) {
            throw new PositiveValueException("O preço deve ser maior que 0");
        }
    }

    private void quantityValidate(Integer value) {
        if (value == null || value < 0) {
            throw new PositiveValueException("a quantidade deve ser maior que 0");
        }
    }

    private Product existingProductValidate(Long id) {
        Optional<Product> existingProductOpt = productRepository.findById(id);
        if (existingProductOpt.isEmpty()) {
            throw new NotFoundException("Produto não encontrado");
        }
        return existingProductOpt.get();
    }

    private void emptyFieldValidate(ProductDTO validate){
        if (StringUtils.isEmpty(validate.name())) {
            throw new EmptyFieldException("O campo 'nome' não pode estar vazio");
        }
    }
}
