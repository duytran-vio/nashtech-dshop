package com.nashtech.dshop_api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.dshop_api.data.entities.Card;
import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.data.repositories.CardRepository;
import com.nashtech.dshop_api.dto.responses.CustomerInfo.CardDto;
import com.nashtech.dshop_api.exceptions.ResourceAlreadyExistException;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException;
import com.nashtech.dshop_api.mappers.CardMapper;
import com.nashtech.dshop_api.services.CardService;
import com.nashtech.dshop_api.services.UserService;

@Service
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService{
    
    private final CardRepository cardRepository;
    private final UserService userService;
    private final CardMapper mapper;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository
                            , UserService userService,
                            CardMapper mapper) {
        this.cardRepository = cardRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    public Card getCardEntityByUserId(Long userId) {
        return cardRepository.findByUserId(userId)
                                .orElseThrow(() -> {
                                    if (!userService.isUserExist(userId)) {
                                        return new ResourceNotFoundException(User.class.getSimpleName(), "id", userId);
                                    }
                                    return new ResourceNotFoundException(Card.class.getSimpleName(), "userId", userId);
                                });
    }

    @Override
    public CardDto getCard(Long userId) {
        Card card = this.getCardEntityByUserId(userId);
        return mapper.toDto(card);
    }

    @Override
    @Transactional
    public CardDto addCard(Long userId, CardDto cardDto) {
        if (cardRepository.findByUserId(userId).isPresent()) {
            throw new ResourceAlreadyExistException(Card.class.getSimpleName(), "userId", userId);
        }
        User user = userService.getUserEntityById(userId);
        Card card = mapper.toEntity(cardDto);
        card.setUser(user);
        card = cardRepository.save(card);
        return mapper.toDto(card);
    }

    @Override
    @Transactional
    public CardDto updateCard(Long userId, CardDto cardDto) {
        Card card = this.getCardEntityByUserId(userId);
        mapper.updateEntityFromDto(card, cardDto);
        return mapper.toDto(cardRepository.save(card));
    }

    @Override
    @Transactional
    public void deleteCard(Long userId) {
        Card card = this.getCardEntityByUserId(userId);
        cardRepository.delete(card);
    }
}
