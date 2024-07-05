package com.compass.ecommerce.controllers.exceptions;

import com.compass.ecommerce.services.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> ProductNotFound(NotFoundException e, HttpServletRequest request){
        StandardError error = new StandardError();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setStatus(HttpStatus.NOT_FOUND.toString()); //404
        error.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ExistingObjectException.class)
    public ResponseEntity<StandardError> existingProduct(ExistingObjectException e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setCode(HttpStatus.CONFLICT.value()); // 409
        error.setStatus(HttpStatus.CONFLICT.toString());
        error.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<StandardError> fieldIsEmpty(EmptyFieldException e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setCode(HttpStatus.BAD_REQUEST.value()); // 400
        error.setStatus(HttpStatus.BAD_REQUEST.toString());
        error.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(PositiveValueException.class)
    public ResponseEntity<StandardError> fieldIsEmpty(PositiveValueException e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setCode(HttpStatus.BAD_REQUEST.value()); // 400
        error.setStatus(HttpStatus.BAD_REQUEST.toString());
        error.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
