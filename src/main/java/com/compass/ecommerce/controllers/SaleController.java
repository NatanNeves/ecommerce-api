package com.compass.ecommerce.controllers;

import com.compass.ecommerce.domain.Product;
import com.compass.ecommerce.domain.Sale;
import com.compass.ecommerce.dtos.ProductDTO;
import com.compass.ecommerce.dtos.SaleDTO;
import com.compass.ecommerce.services.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public ResponseEntity<List<Sale>> findAll() {
        List<Sale> sales = saleService.findAll();
        return ResponseEntity.ok(sales);
    }

    @PostMapping
    public ResponseEntity<Sale> createSale(@Valid @RequestBody SaleDTO saleDTO) {
        Sale newSale = saleService.createSale(saleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSale);
    }

    @GetMapping("/monthly-report")
    public ResponseEntity<List<Sale>> monthlyReport(@RequestParam("yearMonth") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) YearMonth yearMonth) {
        List<Sale> sales = saleService.findAll(); // Aqui você pode usar um método específico para buscar as vendas mensais no serviço
        List<Sale> monthlySales = SaleService.monthlyReport(sales, yearMonth);
        return ResponseEntity.ok(monthlySales);
    }

    @GetMapping("/weekly-report")
    public ResponseEntity<List<Sale>> weeklyReport(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                   @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Sale> sales = saleService.findAll(); // Aqui você pode usar um método específico para buscar as vendas semanais no serviço
        List<Sale> weeklySales = SaleService.weeklyReport(sales, startDate, endDate);
        return ResponseEntity.ok(weeklySales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> findById(@PathVariable Long id) {
        Sale sale = saleService.findById(id);
        return ResponseEntity.ok(sale);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable Long id, @Valid @RequestBody SaleDTO saleDTO) {
        Sale updatedSale = saleService.updateSale(id, saleDTO);
        return ResponseEntity.ok(updatedSale);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
