package com.politechnika.visitservice.controller;


import com.politechnika.visitservice.exeption.VisitCreationException;
import com.politechnika.visitservice.exeption.VisitNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(VisitNotFoundException.class)
    public ResponseEntity<?> handlerVisitNotFoundException(VisitNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

    }
    @ExceptionHandler(VisitCreationException.class)
    public ResponseEntity<?> handlerVisitCreationException(VisitCreationException ex){
        return ResponseEntity.ok().body(ex.getMessage());

    }
}
