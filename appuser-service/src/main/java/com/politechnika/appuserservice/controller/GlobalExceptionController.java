package com.politechnika.appuserservice.controller;

import com.politechnika.appuserservice.exeption.AppUserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<?> handlerAppUserNotFoundException(AppUserNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

    }
}
