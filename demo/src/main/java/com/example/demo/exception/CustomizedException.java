package com.example.demo.exception;

public class CustomizedException extends RuntimeException{
    private String message;

    public CustomizedException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage(){
        return message;
    }
}
