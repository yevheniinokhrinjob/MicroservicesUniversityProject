package com.politechnika.medicineservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(GetPrescriberDataException.class)
    public ResponseEntity<?> handlerAppUserNotFoundException(GetPrescriberDataException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

    }
}
