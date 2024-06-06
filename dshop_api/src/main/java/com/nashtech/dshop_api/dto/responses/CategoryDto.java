package com.nashtech.dshop_api.dto.responses;

import com.nashtech.dshop_api.data.entities.Image;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CategoryDto {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String categoryName;

    private String description;

    private Long layerNum;

    private Long parentId;
    
    private ImageUploadResponse image;
}
