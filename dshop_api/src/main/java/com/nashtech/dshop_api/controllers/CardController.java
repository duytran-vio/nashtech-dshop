package com.nashtech.dshop_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.dshop_api.dto.responses.CustomerInfo.CardDto;
import com.nashtech.dshop_api.services.CardService;
import com.nashtech.dshop_api.utils.Constant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/card")
public class CardController{
    
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getCard(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok()
                            .body(cardService.getCard(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Object> addCard(@PathVariable("userId") Long userId, @Valid @RequestBody CardDto cardDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                            .body(cardService.addCard(userId, cardDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteCard(@PathVariable("userId") Long userId) {
        cardService.deleteCard(userId);
        return ResponseEntity.ok()
                            .body(Constant.DELETE_SUCCESS_MSG);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateCard(@PathVariable("userId") Long userId, @Valid @RequestBody CardDto cardDto) {
        return ResponseEntity.ok()
                            .body(cardService.updateCard(userId, cardDto));
    }
}
