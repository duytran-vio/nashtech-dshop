package com.nashtech.dshop_api.dto.responses.Product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductDetailDto {
    private Long id;
    private String productName;
    private String description;
    private Long reviewNum;
    private Long soldNum;
    private Float price;
    private Long stock;
}
