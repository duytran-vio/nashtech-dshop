package com.nashtech.dshop_api.dto.responses.Product;

import com.nashtech.dshop_api.data.entities.StatusType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductElementDto {
    private Long id;
    private String productName;
    private Float price;
    private Float avgRating;
    private Long soldNum;
    private Long categoryId;
    private StatusType status;
}
