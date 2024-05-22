package com.nashtech.dshop_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nashtech.dshop_api.dto.responses.CustomerInfo.CustomerInfoDto;
import com.nashtech.dshop_api.services.CustomerInfoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("api/customer-info")
public class CustomerInfoController {
    private CustomerInfoService customerInfoService;

    @Autowired
    public CustomerInfoController(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getCustomerInfo(@PathVariable("userId") Long userId) {
        CustomerInfoDto customerInfoDto = customerInfoService.getCustomerInfo(userId);
        return ResponseEntity.ok()
                            .body(customerInfoDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> putCustomerInfo(@PathVariable("userId") Long userId, 
                                                @Valid @RequestBody CustomerInfoDto customerInfoDto) {
        CustomerInfoDto result = customerInfoService.putCustomerInfo(userId, customerInfoDto);
        return ResponseEntity.ok()
                            .body(result);
    }

    // @PatchMapping("/{userId}")
    // public ResponseEntity<Object> patchCustomerInfo(@PathVariable("userId") Long userId, 
    //                                                 @Valid @RequestBody CustomerInfoDto customerInfoDto) {
    //     CustomerInfoDto result = customerInfoService.patchCustomerInfo(userId, customerInfoDto);
    //     return ResponseEntity.ok()
    //                         .body(result);
    // }
}
