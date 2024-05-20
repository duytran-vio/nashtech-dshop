package com.nashtech.dshop_api.exceptions.ResourceNotFoundException;

public class CategoryNotFoundException extends ResourceNotFoundException{
    public CategoryNotFoundException(Long id) {
        super("Not found category with id = " + id);
    }
}
