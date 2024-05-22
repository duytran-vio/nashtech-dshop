package com.nashtech.dshop_api.exceptions.ResourceAlreadyExistException;

public class ResourceAlreadyExistException extends RuntimeException{
    public ResourceAlreadyExistException(String className, String fieldNam, Object fieldValue) {
        super(String.format("%s with %s: %s already exist", className, fieldNam, fieldValue.toString()));
    }
}
