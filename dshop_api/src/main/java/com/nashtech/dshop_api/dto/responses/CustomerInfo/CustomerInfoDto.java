package com.nashtech.dshop_api.dto.responses.CustomerInfo;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CustomerInfoDto {
    
    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Digits(integer = 10, fraction = 0, message = "Phone number is invalid")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    private LocalDateTime dateOfBirth;
}
