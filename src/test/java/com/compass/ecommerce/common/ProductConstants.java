package com.compass.ecommerce.common;

import com.compass.ecommerce.dtos.ProductDTO;

public class ProductConstants {

    public static final ProductDTO PRODUCTDTO = new ProductDTO("name", "description", 99.99, 50);
    public static final ProductDTO EXISTING_PRODUCT_DTO = new ProductDTO("existingName", "existingDescription", 50.0, 100);
}
