package com.compass.ecommerce.controllers;

import com.compass.ecommerce.domain.Product;
import com.compass.ecommerce.dtos.ProductDTO;
import com.compass.ecommerce.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product", description = "Endpoint for managing products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(summary = "Find all products", description = "Find all products", tags = {"Product"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productService.allProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find product by ID", description = "Find a product by its ID", tags = {"Product"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<Product> findById(@PathVariable Long id){
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping()
    @Operation(summary = "Create a new product", description = "Create a new product", tags = {"Product"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDTO  productDTO){
        Product newProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by ID", description = "Delete a product by its ID", tags = {"Product"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product by ID", description = "Update a product by its ID", tags = {"Product"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO) {
        Product updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }
}

