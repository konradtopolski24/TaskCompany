package com.kt.task.exception;

public class InvalidCompanyRequestException extends RuntimeException {

    public InvalidCompanyRequestException(String message) {
        super(message);
    }
}
