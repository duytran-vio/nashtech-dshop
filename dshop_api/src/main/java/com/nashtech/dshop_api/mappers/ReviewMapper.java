package com.nashtech.dshop_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nashtech.dshop_api.data.entities.Review;
import com.nashtech.dshop_api.dto.responses.ReviewDto;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "user", ignore = true)
    public Review toEntity(ReviewDto reviewDto);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "userId", source = "user.id")
    public ReviewDto toDto(Review review);
}
