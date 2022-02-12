package com.example.demo.exception;

public class ApiVersionNotFoundException extends RuntimeException {

    public ApiVersionNotFoundException(Integer version) {
        super("API version %d not found".formatted(version));
    }
}
