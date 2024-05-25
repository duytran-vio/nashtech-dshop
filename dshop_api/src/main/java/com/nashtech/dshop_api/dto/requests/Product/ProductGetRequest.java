package com.nashtech.dshop_api.dto.requests.Product;

import com.nashtech.dshop_api.data.entities.StatusType;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductGetRequest {
    @Nullable
    @Size(min = 3, max = 50, message = "Product name must be between 3 and 50 characters")
    private String productName;
    @Nullable
    private Long categoryId;
    @Nullable
    private StatusType status;
    @Nullable
    private Boolean isFeatured;
}
