package com.compass.ecommerce.repositories;

import com.compass.ecommerce.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("adiciona quantidade ao estoque de produto e retorna sucesso")
    void addStock_case1() {
        // Arrange: cria e salva um produto inicial
        Product product = new Product();
        product.setName("Produto Teste");
        product.setQuantity(10);
        product = productRepository.save(product);

        // Act: adiciona quantidade ao estoque do produto
        int quantityToAdd = 5;
        product.setQuantity(product.getQuantity() + quantityToAdd);
        product = productRepository.save(product);

        // Assert: verifica se a quantidade de estoque foi atualizada corretamente
        Product updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertThat(updatedProduct.getQuantity()).isEqualTo(15);
    }
}
