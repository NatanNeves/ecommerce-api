package com.compass.ecommerce.services.exceptions;

public class EmptyFieldException extends RuntimeException{

    public EmptyFieldException(String msg){
        super(msg);
    }
}
