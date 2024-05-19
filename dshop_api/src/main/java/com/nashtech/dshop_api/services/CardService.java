package com.nashtech.dshop_api.services;

import com.nashtech.dshop_api.dto.responses.CustomerInfo.CardDto;

public interface CardService {
    public CardDto getCard(Long userId);
    public CardDto addCard(Long userId, CardDto cardDto);
    public CardDto updateCard(Long userId, CardDto cardDto);
    public void deleteCard(Long userId);
}
