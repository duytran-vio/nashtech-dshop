package com.nashtech.dshop_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.nashtech.dshop_api.data.entities.Product;
import com.nashtech.dshop_api.dto.requests.Product.ProductCreateUpdateRequest;
import com.nashtech.dshop_api.dto.responses.Product.ProductDetailDto;
import com.nashtech.dshop_api.dto.responses.Product.ProductElementDto;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Mapping(target = "categoryId", source = "category.id")
    public abstract ProductDetailDto toProductDetailDto(Product product);

    @Mapping(target = "categoryId", source = "category.id")
    public abstract ProductElementDto toProductElementDto(Product product);


    @Mapping(target = "reviewNum", constant = "0L")
    @Mapping(target = "avgRating", constant = "0f")
    @Mapping(target = "soldNum", constant = "0L")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "isFeatured", source = "isFeatured", defaultValue = "false")
    @Mapping(target = "status", source = "status", defaultValue = "ACTIVE")
    public abstract Product toEntityFromCreateRequest(ProductCreateUpdateRequest productCreateRequest);

    @Mapping(target = "stock", ignore = true)
    @Mapping(target = "reviewNum", ignore = true)
    @Mapping(target = "avgRating", ignore = true)
    @Mapping(target = "soldNum", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "category", ignore = true)
    public abstract Product toEntityFromUpdateRequest(@MappingTarget Product product,
                                                         ProductCreateUpdateRequest productCreateRequest);
}
