package com.compass.ecommerce.domain;

import com.compass.ecommerce.dtos.ProductDTO;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Product implements Serializable {
    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;
    private String description;

    @Positive
    @Min(value = 1)
    private Double price;
    private Boolean available;

    @Positive
    @Min(value = 1)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @OneToMany(mappedBy = "id.product")
    private Set<Item> items = new HashSet<>();

    Product(ProductDTO data){
        this.name = data.name();
        this.description = data.description();
        this.price = data.price();
        this.quantity = data.quantity();
    }

    public List<Sale> getSales() {
        List<Sale> list = new ArrayList<>();
        if (items != null) {
            for (Item item : items) {
                list.add(item.getSale());
            }
        }
        return list;
    }
}
