package com.nashtech.dshop_api.services;

import com.nashtech.dshop_api.data.entities.CustomerInfo;
import com.nashtech.dshop_api.dto.responses.CustomerInfo.CustomerInfoDto;

public interface CustomerInfoService {
    public CustomerInfo createCustomerInfo(Long userId);
    public CustomerInfoDto getCustomerInfo(Long userId);
    public CustomerInfoDto putCustomerInfo(Long userId, CustomerInfoDto customerInfoDto);
    // public CustomerInfoDto patchCustomerInfo(Long userId, CustomerInfoDto customerInfoDto);
}
