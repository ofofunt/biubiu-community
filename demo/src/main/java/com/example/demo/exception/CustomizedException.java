package com.example.demo.exception;

public class CustomizedException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizedException(ICustomizedExceptionCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public CustomizedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
    public  Integer getCode(){
        return code;
    }
}
