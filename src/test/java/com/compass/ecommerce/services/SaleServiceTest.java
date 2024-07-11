package com.compass.ecommerce.services;

import com.compass.ecommerce.common.SaleConstants;
import com.compass.ecommerce.domain.Item;
import com.compass.ecommerce.domain.Product;
import com.compass.ecommerce.domain.Sale;
import com.compass.ecommerce.dtos.SaleDTO;
import com.compass.ecommerce.repositories.ItemRepository;
import com.compass.ecommerce.repositories.ProductRepository;
import com.compass.ecommerce.repositories.SaleRepository;
import com.compass.ecommerce.repositories.StockRepository;
import com.compass.ecommerce.services.exceptions.InsufficientStockException;
import com.compass.ecommerce.services.exceptions.NotFoundException;
import com.compass.ecommerce.services.exceptions.PositiveValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SaleService.class)
class SaleServiceTest {

    @InjectMocks
    private SaleService saleService;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSale() {
        SaleDTO saleDTO = SaleConstants.SALE_DTO;

        Product productMock = new Product("Product1", "Description1", 100.0, 50);
        when(productRepository.findById(any())).thenReturn(Optional.of(productMock));

        when(productRepository.save(any())).thenReturn(productMock);

        when(saleRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        when(itemRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Sale createdSale = saleService.createSale(saleDTO);

        assertThat(createdSale).isNotNull();
        assertThat(createdSale.getItems()).isNotEmpty();
    }

    @Test
    void createSale_InsufficientStock() {
        SaleDTO saleDTO = SaleConstants.SALE_DTO;

        Product productMock = new Product("Product2", "Description2", 100.0, 1);
        when(productRepository.findById(any())).thenReturn(Optional.of(productMock));

        assertThrows(InsufficientStockException.class, () -> saleService.createSale(saleDTO));
    }

    @Test
    void createSale_EmptyItems() {
        SaleDTO saleDTO = new SaleDTO(new HashSet<>(), 1);

        assertThrows(NotFoundException.class, () -> saleService.createSale(saleDTO));
    }
}
