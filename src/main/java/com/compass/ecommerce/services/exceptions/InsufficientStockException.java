package com.compass.ecommerce.services.exceptions;

public class InsufficientStockException extends RuntimeException{

    public InsufficientStockException(String msg){
        super(msg);
    }
}
