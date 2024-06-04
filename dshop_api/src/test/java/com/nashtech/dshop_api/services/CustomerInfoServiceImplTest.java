package com.nashtech.dshop_api.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.nashtech.dshop_api.data.entities.CustomerInfo;
import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.data.repositories.CustomerInfoRepository;
import com.nashtech.dshop_api.dto.responses.CustomerInfo.CustomerInfoDto;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException;
import com.nashtech.dshop_api.mappers.CustomerInfoMapper;
import com.nashtech.dshop_api.services.UserService;
import com.nashtech.dshop_api.services.impl.CustomerInfoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class CustomerInfoServiceImplTest {

    @Mock
    private CustomerInfoRepository customerInfoRepository;

    @Mock
    private CustomerInfoMapper mapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomerInfoServiceImpl customerInfoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenCreateCustomerInfo_thenReturnCustomerInfo() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userService.getUserEntityById(userId)).thenReturn(user);
        when(customerInfoRepository.save(any(CustomerInfo.class))).thenAnswer(
            invocation -> {
                CustomerInfo customerInfo = invocation.getArgument(0);
                return customerInfo;
            }
        );

        // Act
        CustomerInfo result = customerInfoService.createCustomerInfo(userId);

        // Assert
        assertNotNull(result);
        assertEquals(user, result.getUser());

        verify(userService, times(1)).getUserEntityById(userId);
        verify(customerInfoRepository, times(1)).save(any(CustomerInfo.class));
    }

    @Test
    public void givenExistingCustomerInfo_whenGetCustomerInfoEntity_thenReturnCustomerInfo() {
        // Arrange
        Long userId = 1L;
        CustomerInfo customerInfo = new CustomerInfo();

        when(customerInfoRepository.findByUserId(userId)).thenReturn(Optional.of(customerInfo));

        // Act
        CustomerInfo result = customerInfoService.getCustomerInfoEntity(userId);

        // Assert
        assertNotNull(result);
        assertEquals(customerInfo, result);

        verify(customerInfoRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void givenNonExistingCustomerInfo_whenGetCustomerInfoEntity_thenReturnNewCustomerInfo() {
        // Arrange
        Long userId = 1L;
        User user = new User();

        when(customerInfoRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(userService.getUserEntityById(userId)).thenReturn(user);
        when(customerInfoRepository.save(any(CustomerInfo.class))).thenAnswer(
            invocation -> {
                CustomerInfo customerInfo = invocation.getArgument(0);
                return customerInfo;
            }
        );

        // Act
        CustomerInfo result = customerInfoService.getCustomerInfoEntity(userId);

        // Assert
        assertNotNull(result);

        verify(customerInfoRepository, times(1)).findByUserId(userId);
        verify(userService, times(1)).getUserEntityById(userId);
        verify(customerInfoRepository, times(1)).save(any(CustomerInfo.class));
    }

    @Test
    public void givenExistingCustomerInfo_whenGetCustomerInfo_thenReturnCustomerInfoDto() {
        // Arrange
        Long userId = 1L;
        CustomerInfo customerInfo = new CustomerInfo();
        CustomerInfoDto customerInfoDto = new CustomerInfoDto();

        when(customerInfoRepository.findByUserId(userId)).thenReturn(Optional.of(customerInfo));
        when(mapper.toDto(customerInfo)).thenReturn(customerInfoDto);

        // Act
        CustomerInfoDto result = customerInfoService.getCustomerInfo(userId);

        // Assert
        assertNotNull(result);
        assertEquals(customerInfoDto, result);

        verify(customerInfoRepository, times(1)).findByUserId(userId);
        verify(mapper, times(1)).toDto(customerInfo);
    }

    @Test
    public void givenExistingCustomerInfo_whenPutCustomerInfo_thenReturnUpdatedCustomerInfoDto() {
        // Arrange
        Long userId = 1L;
        CustomerInfoDto customerInfoDto = new CustomerInfoDto();
        customerInfoDto.setFullName("New name");

        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setFullName("Old name");

        when(customerInfoRepository.findByUserId(userId)).thenReturn(Optional.of(customerInfo));
        when(mapper.putEntityFromDto(customerInfoDto, customerInfo)).thenAnswer(
            invocation -> {
                CustomerInfoDto dto = invocation.getArgument(0);
                CustomerInfo entity = invocation.getArgument(1);
                entity.setFullName(dto.getFullName());
                return entity;
            }
        );
        when(customerInfoRepository.save(customerInfo)).thenReturn(customerInfo);
        when(mapper.toDto(customerInfo)).thenReturn(customerInfoDto);

        // Act
        CustomerInfoDto result = customerInfoService.putCustomerInfo(userId, customerInfoDto);

        // Assert
        assertNotNull(result);
        assertEquals(customerInfoDto, result);
        assertEquals(customerInfoDto.getFullName(), result.getFullName());

        verify(customerInfoRepository, times(1)).findByUserId(userId);
        verify(mapper, times(1)).putEntityFromDto(customerInfoDto, customerInfo);
        verify(customerInfoRepository, times(1)).save(customerInfo);
        verify(mapper, times(1)).toDto(customerInfo);
    }

    // @Test
    // public void givenNonExistingCustomerInfo_whenPutCustomerInfo_thenThrowResourceNotFoundException() {
    //     // Arrange
    //     Long userId = 1L;
    //     CustomerInfoDto customerInfoDto = new CustomerInfoDto();

    //     when(customerInfoRepository.findByUserId(userId)).thenReturn(Optional.empty());

    //     // Act & Assert
    //     assertThrows(ResourceNotFoundException.class, () -> customerInfoService.putCustomerInfo(userId, customerInfoDto));

    //     verify(customerInfoRepository, times(1)).findByUserId(userId);
    //     verify(mapper, never()).putEntityFromDto(any(CustomerInfoDto.class), any(CustomerInfo.class));
    //     verify(customerInfoRepository, never()).save(any(CustomerInfo.class));
    //     verify(mapper, never()).toDto(any(CustomerInfo.class));
    // }

    // @Test
    // public void whenPatchCustomerInfo_existingCustomerInfo_thenReturnUpdatedCustomerInfoDto() {
    //     // Arrange
    //     Long userId = 1L;
    //     CustomerInfoDto customerInfoDto = new CustomerInfoDto();
    //     CustomerInfo customerInfo = new CustomerInfo();

    //     when(customerInfoService.getCustomerInfoEntity(userId)).thenReturn(customerInfo);
    //     when(mapper.patchEntityFromDto(customerInfoDto, customerInfo)).thenReturn(customerInfo);
    //     when(customerInfoRepository.save(customerInfo)).thenReturn(customerInfo);
    //     when(mapper.toDto(customerInfo)).thenReturn(customerInfoDto);

    //     // Act
    //     CustomerInfoDto result = customerInfoService.patchCustomerInfo(userId, customerInfoDto);

    //     // Assert
    //     assertNotNull(result);
    //     assertEquals(customerInfoDto, result);

    //     verify(customerInfoService, times(1)).getCustomerInfoEntity(userId);
    //     verify(mapper, times(1)).patchEntityFromDto(customerInfoDto, customerInfo);
    //     verify(customerInfoRepository, times(1)).save(customerInfo);
    //     verify(mapper, times(1)).toDto(customerInfo);
    // }

    // @Test
    // public void whenPatchCustomerInfo_nonExistingCustomerInfo_thenThrowResourceNotFoundException() {
    //     // Arrange
    //     Long userId = 1L;
    //     CustomerInfoDto customerInfoDto = new CustomerInfoDto();

    //     when(customerInfoService.getCustomerInfoEntity(userId)).thenReturn(null);

    //     // Act & Assert
    //     assertThrows(ResourceNotFoundException.class, () -> customerInfoService.patchCustomerInfo(userId, customerInfoDto));

    //     verify(customerInfoService, times(1)).getCustomerInfoEntity(userId);
    //     verify(mapper, never()).patchEntityFromDto(any(CustomerInfoDto.class), any(CustomerInfo.class));
    //     verify(customerInfoRepository, never()).save(any(CustomerInfo.class));
    //     verify(mapper, never()).toDto(any(CustomerInfo.class));
    // }
}