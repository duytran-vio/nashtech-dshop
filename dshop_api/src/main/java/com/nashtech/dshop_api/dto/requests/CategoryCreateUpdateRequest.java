package com.nashtech.dshop_api.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CategoryCreateUpdateRequest {
    
    @NotBlank
    @Size(min = 3, max = 50)
    private String categoryName;

    private String description;

    private Long parentId;

    private Long imageId;
}
