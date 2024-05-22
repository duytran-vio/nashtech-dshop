package com.nashtech.dshop_api.dto.requests.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductCreateUpdateRequest {
    
    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 50, message = "Product name must be between 3 and 50 characters")
    private String productName;
    private String description;

    // @NotBlank(message = "Product price is required")
    @Positive(message = "Product price must be a positive number")
    private Float price;

    @Positive(message = "Product stock must be a positive number")
    private Long stock;

    // @NotBlank(message = "Product category is required")
    private Long categoryId;

    private String status;

    // @NotBlank(message = "Product create user id is required")
    private Long createUserId;
}
