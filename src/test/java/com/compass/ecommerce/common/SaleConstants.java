package com.compass.ecommerce.common;

import com.compass.ecommerce.domain.Item;
import com.compass.ecommerce.domain.Product;
import com.compass.ecommerce.dtos.SaleDTO;

import java.util.HashSet;
import java.util.Set;

public class SaleConstants {

    public static final SaleDTO SALE_DTO = createSaleDTO();
    public static final SaleDTO EXISTING_SALE_DTO = createExistingSaleDTO();

    private static SaleDTO createSaleDTO() {
        Set<Item> items = new HashSet<>();
        items.add(new Item(new Product("Product1", "Description1", 100.0, 10), null, 2, 49.99));
        items.add(new Item(new Product("Product2", "Description2", 50.0, 5), null, 3, 29.99));
        return new SaleDTO(items, items.size());
    }

    private static SaleDTO createExistingSaleDTO() {
        return new SaleDTO(new HashSet<>(), 0);
    }
}
