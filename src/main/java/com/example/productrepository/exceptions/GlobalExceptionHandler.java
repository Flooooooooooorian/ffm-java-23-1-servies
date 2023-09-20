package com.example.productrepository.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoSuchElementException.class, NullPointerException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNoSuchElementException(Exception exception) {
        return new ErrorMessage("Ups, hier ist etwas schief gelaufen..." + exception.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleExceptions(Exception exception) {
        System.out.println("ACHTUNG UNERWARTETER FEHLER");
        return new ErrorMessage("Ups, hier ist etwas schief gelaufen..." + exception.getMessage());
    }
}
