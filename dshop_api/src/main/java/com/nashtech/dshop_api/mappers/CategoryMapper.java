package com.nashtech.dshop_api.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.nashtech.dshop_api.data.entities.Category;
import com.nashtech.dshop_api.dto.requests.CategoryCreateUpdateRequest;
import com.nashtech.dshop_api.dto.responses.CategoryDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    
    @Mapping(source = "parentCategory.id", target = "parentId")
    @Mapping(source = "image", target = "image")
    public CategoryDto toDto(Category category);

    @Mapping(source = "parentId", target = "parentCategory", ignore = true)
    @Mapping(source = "imageId", target = "image", ignore = true)
    public Category toEntityFromRequest(CategoryCreateUpdateRequest categoryDto);

    
    @Mapping(source = "parentId", target = "parentCategory", ignore = true)
    @Mapping(source = "imageId", target = "image", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public Category updateEntityFromRequest(CategoryCreateUpdateRequest categoryDto, @MappingTarget Category category);
}
