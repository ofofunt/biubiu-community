package com.example.demo.exception;

public enum CustomizedErrorCode implements  ICustomizedExceptionCode{
    QUESTION_NOT_FOUND("问题不存在,要不换个试试？");
    private String message;

    CustomizedErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
