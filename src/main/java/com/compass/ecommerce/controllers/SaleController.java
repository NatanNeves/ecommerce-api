package com.compass.ecommerce.controllers;

import com.compass.ecommerce.domain.Sale;
import com.compass.ecommerce.dtos.SaleDTO;
import com.compass.ecommerce.services.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/sales")
@Tag(name = "Sale", description = "Endpoint for managing sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    @Operation(summary = "Find all sales", description = "Find all sales", tags = {"Sale"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<List<Sale>> findAll() {
        List<Sale> sales = saleService.findAll();
        return ResponseEntity.ok(sales);
    }

    @PostMapping
    @Operation(summary = "Create a new sale", description = "Create a new sale", tags = {"Sale"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<Sale> createSale(@Valid @RequestBody SaleDTO saleDTO) {
        Sale newSale = saleService.createSale(saleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSale);
    }

    @GetMapping("/monthly-report")
    @Operation(summary = "Get monthly sales report", description = "Get a report of sales for a specific month", tags = {"Sale"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<List<Sale>> monthlyReport(@RequestParam("yearMonth") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) YearMonth yearMonth) {
        List<Sale> sales = saleService.findAll(); // Aqui você pode usar um método específico para buscar as vendas mensais no serviço
        List<Sale> monthlySales = SaleService.monthlyReport(sales, yearMonth);
        return ResponseEntity.ok(monthlySales);
    }

    @GetMapping("/weekly-report")
    @Operation(summary = "Get weekly sales report", description = "Get a report of sales for a specific week", tags = {"Sale"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<List<Sale>> weeklyReport(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                   @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Sale> sales = saleService.findAll(); // Aqui você pode usar um método específico para buscar as vendas semanais no serviço
        List<Sale> weeklySales = SaleService.weeklyReport(sales, startDate, endDate);
        return ResponseEntity.ok(weeklySales);
    }

    @GetMapping("/sales")
    @Operation(summary = "Get sales by date range", description = "Get sales within a specific date range", tags = {"Sale"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    public List<Sale> getSales(@RequestParam String startDate, @RequestParam String endDate) {
        return saleService.getSalesByDate(startDate, endDate);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find sale by ID", description = "Find a sale by its ID", tags = {"Sale"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<Sale> findById(@PathVariable Long id) {
        Sale sale = saleService.findById(id);
        return ResponseEntity.ok(sale);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update sale by ID", description = "Update a sale by its ID", tags = {"Sale"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<Sale> updateSale(@PathVariable Long id, @Valid @RequestBody SaleDTO saleDTO) {
        Sale updatedSale = saleService.updateSale(id, saleDTO);
        return ResponseEntity.ok(updatedSale);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete sale by ID", description = "Delete a sale by its ID", tags = {"Sale"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
