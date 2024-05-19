package com.nashtech.dshop_api.exceptions.ResourceNotFoundException;

public class CardNotFoundException extends ResourceNotFoundException{
    public CardNotFoundException(Long userId) {
        super("Card not found with user id: " + userId);
    }
}
