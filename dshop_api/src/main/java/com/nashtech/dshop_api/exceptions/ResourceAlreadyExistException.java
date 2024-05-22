package com.nashtech.dshop_api.exceptions;

public class ResourceAlreadyExistException extends RuntimeException{
    public ResourceAlreadyExistException(String className, String fieldNam, Object fieldValue) {
        super(String.format("%s with %s: %s already exist", className, fieldNam, fieldValue.toString()));
    }
}
