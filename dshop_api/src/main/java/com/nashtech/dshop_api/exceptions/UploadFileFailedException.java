package com.nashtech.dshop_api.exceptions;

public class UploadFileFailedException extends RuntimeException{
    public UploadFileFailedException(String fileName) {
        super("Failed to upload file: " + fileName);
    }
}
