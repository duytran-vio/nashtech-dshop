package com.nashtech.dshop_api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nashtech.dshop_api.data.entities.CustomerInfo;
import com.nashtech.dshop_api.data.repositories.CustomerInfoRepository;
import com.nashtech.dshop_api.dto.responses.CustomerInfo.CustomerInfoDto;
import com.nashtech.dshop_api.mappers.CustomerInfoMapper;
import com.nashtech.dshop_api.services.CustomerInfoService;
import com.nashtech.dshop_api.services.UserService;

@Service
public class CustomerInfoServiceImpl implements CustomerInfoService{

    private CustomerInfoRepository customerInfoRepository;
    private CustomerInfoMapper mapper;
    private UserService userService;
    
    @Autowired
    public CustomerInfoServiceImpl(CustomerInfoRepository customerInfoRepository, 
                                    UserService userService,
                                    CustomerInfoMapper mapper) {
        this.customerInfoRepository = customerInfoRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public CustomerInfo createCustomerInfo(Long userId){
        var user = userService.getUserEntityById(userId);
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setUser(user);
        return customerInfoRepository.save(customerInfo);
    }

    public CustomerInfo getCustomerInfoEntity(Long userId){   
        var customerInfo = customerInfoRepository.findByUserId(userId)
                                             .orElseGet(() -> this.createCustomerInfo(userId));
        return customerInfo;
    }

    @Override
    public CustomerInfoDto getCustomerInfo(Long userId) {
        CustomerInfo customerInfo = this.getCustomerInfoEntity(userId);
        return mapper.toDto(customerInfo);
    }

    @Override
    public CustomerInfoDto putCustomerInfo(Long userId, CustomerInfoDto customerInfoDto) {
        CustomerInfo customerInfo = getCustomerInfoEntity(userId);
        mapper.putEntityFromDto(customerInfoDto, customerInfo);
        return mapper.toDto(customerInfoRepository.save(customerInfo));
    }

    // @Override
    // public CustomerInfoDto patchCustomerInfo(Long userId, CustomerInfoDto customerInfoDto) {
    //     CustomerInfo customerInfo = getCustomerInfoEntity(userId);
    //     mapper.patchEntityFromDto(customerInfoDto, customerInfo);
    //     return mapper.toDto(customerInfoRepository.save(customerInfo));
    // }
}
