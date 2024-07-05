package com.compass.ecommerce.services.exceptions;

public class PositiveValueException extends RuntimeException{

    public PositiveValueException(String msg){
        super(msg);
    }
}
