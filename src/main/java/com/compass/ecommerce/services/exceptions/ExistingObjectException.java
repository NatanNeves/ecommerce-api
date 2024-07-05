package com.compass.ecommerce.services.exceptions;

public class ExistingObjectException extends RuntimeException{

    public ExistingObjectException(String msg){
        super(msg);
    }
}
