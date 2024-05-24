package com.nashtech.dshop_api.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.nashtech.dshop_api.data.entities.Card;
import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.data.repositories.CardRepository;
import com.nashtech.dshop_api.dto.responses.CustomerInfo.CardDto;
import com.nashtech.dshop_api.exceptions.ResourceAlreadyExistException;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException;
import com.nashtech.dshop_api.mappers.CardMapper;
import com.nashtech.dshop_api.services.UserService;
import com.nashtech.dshop_api.services.impl.CardServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserService userService;

    @Mock
    private CardMapper mapper;

    @InjectMocks
    private CardServiceImpl cardService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetCardEntityByUserId_thenReturnCard() {
        // Arrange
        Long userId = 1L;
        Card card = new Card();
        card.setId(1L);

        when(cardRepository.findByUserId(userId)).thenReturn(Optional.of(card));

        // Act
        Card result = cardService.getCardEntityByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(card.getId(), result.getId());

        verify(cardRepository, times(1)).findByUserId(userId);
        verify(userService, never()).isUserExist(userId);
    }

    @Test
    public void givenNonExistingUser_whenGetCardEntityByUserId_thenThrowResourceNotFoundException() {
        // Arrange
        Long userId = 1L;

        when(cardRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(userService.isUserExist(userId)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> cardService.getCardEntityByUserId(userId));

        verify(cardRepository, times(1)).findByUserId(userId);
        verify(userService, times(1)).isUserExist(userId);
    }

    @Test
    public void givenNonExistingCard_whenGetCardEntityByUserId_thenThrowResourceNotFoundException() {
        // Arrange
        Long userId = 1L;

        when(cardRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(userService.isUserExist(userId)).thenReturn(true);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> cardService.getCardEntityByUserId(userId));

        verify(cardRepository, times(1)).findByUserId(userId);
        verify(userService, times(1)).isUserExist(userId);
    }

    @Test
    public void whenGetCard_thenReturnCardDto() {
        // Arrange
        Long userId = 1L;
        Card card = new Card();
        card.setId(1L);
        CardDto cardDto = new CardDto();
        cardDto.setId(1L);

        when(cardRepository.findByUserId(userId)).thenReturn(Optional.of(card));
        when(mapper.toDto(card)).thenReturn(cardDto);

        // Act
        CardDto result = cardService.getCard(userId);

        // Assert
        assertNotNull(result);
        assertEquals(cardDto.getId(), result.getId());

        verify(cardRepository, times(1)).findByUserId(userId);
        verify(userService, never()).isUserExist(userId);
        verify(mapper, times(1)).toDto(card);
    }

    @Test
    public void givenExistingCard_whenAddCard_thenThrowResourceAlreadyExistException() {
        // Arrange
        Long userId = 1L;
        CardDto cardDto = new CardDto();
        Card existingCard = new Card();

        when(cardRepository.findByUserId(userId)).thenReturn(Optional.of(existingCard));

        // Act & Assert
        assertThrows(ResourceAlreadyExistException.class, () -> cardService.addCard(userId, cardDto));

        verify(cardRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void whenAddCard_thenReturnCardDto() {
        // Arrange
        Long userId = 1L;
        CardDto cardDto = new CardDto();
        User user = new User();
        user.setId(userId);
        Card card = new Card();
        CardDto savedCardDto = new CardDto();

        when(cardRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(userService.getUserEntityById(userId)).thenReturn(user);
        when(mapper.toEntity(cardDto)).thenReturn(card);
        when(cardRepository.save(card)).thenReturn(card);
        when(mapper.toDto(card)).thenReturn(savedCardDto);

        // Act
        CardDto result = cardService.addCard(userId, cardDto);

        // Assert
        assertNotNull(result);
        assertEquals(savedCardDto, result);
        assertEquals(card.getUser().getId(), userId);

        verify(cardRepository, times(1)).findByUserId(userId);
        verify(userService, times(1)).getUserEntityById(userId);
        verify(mapper, times(1)).toEntity(cardDto);
        verify(cardRepository, times(1)).save(card);
        verify(mapper, times(1)).toDto(card);
    }

    @Test
    public void whenUpdateCard_thenReturnCardDto() {
        // Arrange
        Long userId = 1L;
        CardDto cardDto = new CardDto();
        cardDto.setCardHolder("New holder");

        Card card = new Card();
        cardDto.setCardHolder("Old holder");

        CardDto updatedCardDto = new CardDto();
        updatedCardDto.setCardHolder(cardDto.getCardHolder());

        when(cardRepository.findByUserId(userId)).thenReturn(Optional.of(card));
        // when(mapper.updateEntityFromDto(card, cardDto)).thenReturn(updatedCard);
        when(mapper.updateEntityFromDto(any(Card.class), any(CardDto.class))).thenAnswer(invocation -> {
            Card argCard = invocation.getArgument(0);
            CardDto argCardDto = invocation.getArgument(1);
            // Modify the card object based on the cardDto object
            argCard.setCardHolder(argCardDto.getCardHolder());
            return null; // this method is void, so we return null
        });
        when(cardRepository.save(card)).thenReturn(card);
        when(mapper.toDto(card)).thenReturn(updatedCardDto);

        // Act
        CardDto result = cardService.updateCard(userId, cardDto);

        // Assert
        assertNotNull(result);
        assertEquals(updatedCardDto, result);
        assertEquals(result.getCardHolder(), cardDto.getCardHolder());
        assertEquals(card.getCardHolder(), cardDto.getCardHolder()); 

        verify(cardRepository, times(1)).findByUserId(userId);
        verify(mapper, times(1)).updateEntityFromDto(card, cardDto);
        verify(cardRepository, times(1)).save(card);
        verify(mapper, times(1)).toDto(card);
    }

    @Test
    public void whenDeleteCard_ReturnNone() {
        // Arrange
        Long userId = 1L;
        Card card = new Card();

        when(cardRepository.findByUserId(userId)).thenReturn(Optional.of(card));

        // Act
        assertDoesNotThrow(() -> cardService.deleteCard(userId));

        // Assert
        verify(cardRepository, times(1)).findByUserId(userId);
        verify(cardRepository, times(1)).delete(card);
    }

    @Test
    public void givenNonExistingCard_whenDeleteCard_ThrowsResourceNotFoundException() {
        // Arrange
        Long userId = 1L;

        when(cardRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> cardService.deleteCard(userId));

        verify(cardRepository, times(1)).findByUserId(userId);
        verify(cardRepository, never()).delete(any(Card.class));
    }
}