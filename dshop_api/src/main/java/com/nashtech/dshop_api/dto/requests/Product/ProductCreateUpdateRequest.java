package com.nashtech.dshop_api.dto.requests.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Positive(message = "Product price must be a positive number")
    private Float price;

    @NotNull
    @Positive(message = "Product stock must be a positive number")
    private Long stock;

    private Long categoryId;

    private String status;

    private Boolean isFeatured;

    private Long createUserId;
}
