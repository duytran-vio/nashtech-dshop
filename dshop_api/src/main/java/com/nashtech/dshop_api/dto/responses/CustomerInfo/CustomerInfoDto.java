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
    private String fullName;

    // @NotBlank(message = "Phone number is required")
    // @Digits(integer = 11, fraction = 0, message = "Phone number must be 11 digits")
    private String phone;
    
    private String address;

    private LocalDateTime dateOfBirth;
}
