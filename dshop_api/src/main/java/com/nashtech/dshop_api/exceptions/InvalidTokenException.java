package com.nashtech.dshop_api.exceptions;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException() {
        super("Invalid token. Please login again.");
    }
}
