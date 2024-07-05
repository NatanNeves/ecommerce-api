package com.compass.ecommerce.services.exceptions;

public class ExistingProductException extends RuntimeException{

    public ExistingProductException(String msg){
        super(msg);
    }
}
