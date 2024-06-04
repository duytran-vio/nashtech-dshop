package com.nashtech.dshop_api.mappers;

import org.mapstruct.Mapper;

import com.nashtech.dshop_api.data.entities.Image;
import com.nashtech.dshop_api.dto.responses.ImageUploadResponse;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    public ImageUploadResponse toImageUploadResponse(Image image);
}
