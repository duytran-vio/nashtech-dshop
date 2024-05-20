package com.nashtech.dshop_api.exceptions.ResourceAlreadyExistException;

public class CategoryAlreadyExistException extends ResourceAlreadyExistException {
    public CategoryAlreadyExistException(String categoryName) {
        super("Category with name = " + categoryName + " already exist");
    }
    
}
