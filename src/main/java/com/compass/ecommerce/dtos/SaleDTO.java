package com.compass.ecommerce.dtos;

import com.compass.ecommerce.domain.Item;

import java.util.Set;

public record SaleDTO (Set<Item> items, Integer quantity){
}
