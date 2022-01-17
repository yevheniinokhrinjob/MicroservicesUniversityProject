package com.politechnika.visitservice.exeption;

public class VisitNotFoundException extends RuntimeException {
    public VisitNotFoundException(String message){
        super(message);
    }
}
