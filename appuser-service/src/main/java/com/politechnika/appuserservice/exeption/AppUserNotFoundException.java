package com.politechnika.appuserservice.exeption;

public class AppUserNotFoundException extends RuntimeException {
    public AppUserNotFoundException(String message){
        super(message);
    }
}
