
package com.nashtech.dshop_api.dto.responses.CustomerInfo;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CardDto {
    @NotBlank
    @Digits(integer = 16, fraction = 0, message = "Card number is invalid")
    private String cardNumber;

    @NotBlank(message = "Card holder is required")
    private String cardHolder;

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) (?:[01]\\d|2[0-3]):[0-5][0-9]:[0-5][0-9]$", 
            message = "Invalid date format. Expected format: yyyy-MM-dd HH:mm:ss")
    private String expiredDate;

    @NotBlank(message = "CVV is required")
    @Digits(integer = 3, fraction = 0, message = "CVV is invalid")
    private String cvv;
}