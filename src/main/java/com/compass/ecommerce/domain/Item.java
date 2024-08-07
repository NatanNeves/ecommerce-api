package com.compass.ecommerce.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {
    private static final long SerialVersionUID = 1L;

    @EmbeddedId
    private ItemPK id = new ItemPK();
    private Product product;
    private Sale sale;
    private Integer quantity;
    private Double price;
    private Double subtotal;

    public Item(Product product, Sale sale, Integer quantity, Double price) {
        this.id = new ItemPK();
        this.id.setProduct(product);
        this.id.setSale(sale);
        this.product = product;
        this.sale = sale;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = quantity * price;
    }
}
