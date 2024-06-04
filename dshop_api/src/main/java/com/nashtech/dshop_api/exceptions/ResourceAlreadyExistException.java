package com.nashtech.dshop_api.exceptions;

public class ResourceAlreadyExistException extends RuntimeException{
    public ResourceAlreadyExistException(String className, String fieldName, Object fieldValue) {
        super(String.format("%s with %s: %s already exist", className, fieldName, fieldValue.toString()));
    }
}
