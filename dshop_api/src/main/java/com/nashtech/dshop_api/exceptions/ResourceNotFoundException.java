package com.nashtech.dshop_api.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String className, String fieldName, Object fieldValue) {
        super(String.format("%s with %s: %s not found", className, fieldName, fieldValue.toString()));
    }
}
