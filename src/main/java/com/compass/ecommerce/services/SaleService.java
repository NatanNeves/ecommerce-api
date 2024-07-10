package com.compass.ecommerce.services;

import com.compass.ecommerce.domain.*;
import com.compass.ecommerce.dtos.SaleDTO;
import com.compass.ecommerce.repositories.ItemRepository;
import com.compass.ecommerce.repositories.ProductRepository;
import com.compass.ecommerce.repositories.SaleRepository;
import com.compass.ecommerce.repositories.StockRepository;
import com.compass.ecommerce.services.exceptions.InsufficientStockException;
import com.compass.ecommerce.services.exceptions.NotFoundException;
import com.compass.ecommerce.services.exceptions.PositiveValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProductRepository productRepository;


    @Cacheable("AllSales")
    public List<Sale> findAll(){
        return saleRepository.findAll();
    }

    @Cacheable(value = "sales", key = "#id")
    public Sale findById(Long id) {
        Optional<Sale> sale = saleRepository.findById(id);
        return sale.orElseThrow(() -> new NotFoundException("Venda não encontrada"));
    }

    @Caching(evict = {
            @CacheEvict(value = "allSales", allEntries = true),
            @CacheEvict(value = "sales", key = "#sale.id")
    })
    public Sale createSale(SaleDTO saleDTO) {
        // Verifica se há pelo menos um item no DTO
        if (saleDTO.items().isEmpty()) {
            throw new NotFoundException("A venda deve conter pelo menos um item");
        }

        Sale sale = new Sale();
        sale.setTimestamp(Instant.now());
        Double totalVenda = 0.0;

        Set<Item> items = saleDTO.items(); // Corrigido para usar getItems() em vez de items()
        Set<Item> itemsToSave = new HashSet<>();

        for (Item itemDTO : items) {
            // Verifica se o preço e a quantidade são válidos
            if (itemDTO.getPrice() != null && itemDTO.getQuantity() != null) {
                // Configura a referência de produto para o item
                Product product = productRepository.findById(itemDTO.getProduct().getId())
                        .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

                // Verifica se há estoque suficiente para o produto
                if (product.getQuantity() < itemDTO.getQuantity()) {
                    throw new InsufficientStockException("Estoque insuficiente para o produto: " + product.getName());
                }

                // Decrementa o estoque do produto
                product.setQuantity(product.getQuantity() - itemDTO.getQuantity());
                productRepository.save(product);

                Item item = new Item(product, sale, itemDTO.getQuantity(), itemDTO.getPrice());
                itemsToSave.add(item);

                totalVenda += item.getSubtotal();
            } else {
                throw new PositiveValueException("Preço ou quantidade inválidos");
            }
        }

        // Primeiro salva a venda
        sale.setTotal(totalVenda);
        sale = saleRepository.save(sale);

        // Depois salva os itens
        for (Item item : itemsToSave) {
            itemRepository.save(item);
            sale.getItems().add(item); // Atualiza a lista de itens da venda
        }

        return sale;
    }

    public Sale updateSale(Long saleId, SaleDTO saleDTO) {
        // Busca a venda existente pelo ID
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new NotFoundException("Venda não encontrada"));

        // Limpa os itens existentes da venda para recriá-los
        sale.getItems().clear();
        Double totalVenda = 0.0;

        Set<Item> items = saleDTO.items(); // Usa getItems() em vez de items()
        Set<Item> itemsToSave = new HashSet<>();

        for (Item itemDTO : items) {
            // Verifica se o preço e a quantidade são válidos
            if (itemDTO.getPrice() != null && itemDTO.getQuantity() != null) {
                // Configura a referência de produto para o item
                Product product = productRepository.findById(itemDTO.getProduct().getId())
                        .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

                // Cria um novo item associado à venda existente
                Item item = new Item(product, sale, itemDTO.getQuantity(), itemDTO.getPrice());
                itemsToSave.add(item);

                totalVenda += item.getSubtotal();
            } else {
                throw new PositiveValueException("Preço ou quantidade inválidos");
            }
        }

        // Atualiza o total da venda
        sale.setTotal(totalVenda);

        // Salva os itens atualizados
        for (Item item : itemsToSave) {
            itemRepository.save(item);
            sale.getItems().add(item); // Atualiza a lista de itens da venda
        }

        // Salva a venda atualizada no banco de dados
        sale = saleRepository.save(sale);

        return sale;
    }

    public void deleteSale(Long saleId) {
        // Busca a venda pelo ID
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new NotFoundException("Venda não encontrada"));

        // Remove os itens associados à venda
        sale.getItems().clear();

        // Remove a venda do repositório
        saleRepository.delete(sale);
    }

    public List<Sale> getSales(String startDate, String endDate) {
        Instant start = Instant.parse(startDate);
        Instant end = Instant.parse(endDate);
        return saleRepository.findByTimestampBetween(start, end);
    }

    public static List<Sale> monthlyReport(List<Sale> sales, YearMonth yearMonth) {
        Instant startOfMonth = yearMonth.atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endOfMonth = yearMonth.atEndOfMonth().atStartOfDay().toInstant(ZoneOffset.UTC);

        return sales.stream()
                .filter(sale -> sale.getTimestamp().isAfter(startOfMonth) && sale.getTimestamp().isBefore(endOfMonth))
                .collect(Collectors.toList());
    }

    public static List<Sale> weeklyReport(List<Sale> sales, LocalDate startDate, LocalDate endDate) {
        Instant startOfWeek = startDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endOfWeek = endDate.atTime(23, 59, 59).toInstant(ZoneOffset.UTC);

        return sales.stream()
                .filter(sale -> sale.getTimestamp().isAfter(startOfWeek) && sale.getTimestamp().isBefore(endOfWeek))
                .collect(Collectors.toList());
    }
}


