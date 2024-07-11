package com.compass.ecommerce.services;

import com.compass.ecommerce.common.ProductConstants;
import com.compass.ecommerce.domain.Product;
import com.compass.ecommerce.dtos.ProductDTO;
import com.compass.ecommerce.repositories.ItemRepository;
import com.compass.ecommerce.repositories.ProductRepository;
import com.compass.ecommerce.repositories.StockRepository;
import com.compass.ecommerce.services.exceptions.ExistingObjectException;
import com.compass.ecommerce.services.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.compass.ecommerce.common.ProductConstants.PRODUCTDTO;
import static com.compass.ecommerce.common.ProductConstants.EXISTING_PRODUCT_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void allProducts() {

    }

    @Test
    void findById() {
        Product product = new Product("name", "description", 99.99, 50);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product foundProduct = productService.findById(1L);
        assertThat(foundProduct).isEqualTo(product);
    }

    @Test
    void findById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.findById(1L));
    }

    @Test
    void createProduct() {
        when(productRepository.findByName(PRODUCTDTO.name())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);

        Product sut = productService.createProduct(PRODUCTDTO);

        assertThat(sut.getName()).isEqualTo(PRODUCTDTO.name());
        assertThat(sut.getDescription()).isEqualTo(PRODUCTDTO.description());
        assertThat(sut.getPrice()).isEqualTo(PRODUCTDTO.price());
        assertThat(sut.getQuantity()).isEqualTo(PRODUCTDTO.quantity());
    }

    @Test
    void createProduct_ExistingProduct() {
        when(productRepository.findByName(EXISTING_PRODUCT_DTO.name())).thenReturn(Optional.of(new Product()));

        assertThrows(ExistingObjectException.class, () -> productService.createProduct(EXISTING_PRODUCT_DTO));
    }

    @Test
    void updateProduct() {
        Product existingProduct = new Product("name", "description", 99.99, 50);
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);

        ProductDTO updatedProductDTO = new ProductDTO("newName", "newDescription", 100.0, 60);
        Product updatedProduct = productService.updateProduct(1L, updatedProductDTO);

        assertThat(updatedProduct.getName()).isEqualTo(updatedProductDTO.name());
        assertThat(updatedProduct.getDescription()).isEqualTo(updatedProductDTO.description());
        assertThat(updatedProduct.getPrice()).isEqualTo(updatedProductDTO.price());
        assertThat(updatedProduct.getQuantity()).isEqualTo(updatedProductDTO.quantity());
    }

    @Test
    void deleteProduct() {
        Product product = new Product("name", "description", 99.99, 50);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        assertThat(product.getAvailable()).isFalse();
    }
}
