package com.nashtech.dshop_api.dto.responses.Product;

import java.util.List;

import com.nashtech.dshop_api.data.entities.StatusType;
import com.nashtech.dshop_api.dto.responses.ImageUploadResponse;

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
    private Float avgRating;
    private Long reviewNum;
    private Long soldNum;
    private Float price;
    private Long stock;
    private Long categoryId;
    private StatusType status;
    private Boolean isFeatured;
    private List<ImageUploadResponse> images;
}
