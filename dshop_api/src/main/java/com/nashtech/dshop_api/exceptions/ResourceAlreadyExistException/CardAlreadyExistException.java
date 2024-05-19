package com.nashtech.dshop_api.exceptions.AlreadyExistResourceException;

public class CardAlreadyExistException extends ResourceAlreadyExistException{
    public CardAlreadyExistException(Long userId) {
        super("Card already exist with user id: " + userId);
    }
}
