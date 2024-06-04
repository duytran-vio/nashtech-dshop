package com.nashtech.dshop_api.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.nashtech.dshop_api.data.entities.Image;
import com.nashtech.dshop_api.data.entities.Product;
import com.nashtech.dshop_api.dto.requests.Product.ProductCreateUpdateRequest;
import com.nashtech.dshop_api.dto.responses.ImageUploadResponse;
import com.nashtech.dshop_api.dto.responses.Product.ProductDetailDto;
import com.nashtech.dshop_api.dto.responses.Product.ProductElementDto;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    private ImageMapper imageMapper;

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "images", source = "images")
    public abstract ProductDetailDto toProductDetailDto(Product product);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "thumbnailUrl", source = "images", qualifiedByName = "toThumbnailUrl")
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

    public List<ImageUploadResponse> toImageUploadResponse(List<Image> images) {
        return images.stream().map(imageMapper::toImageUploadResponse).collect(Collectors.toList());
    }

    @Named("toThumbnailUrl")
    public String toThumbnailUrl(List<Image> images) {
        // return images.stream().filter(Image::getIsThumb).findFirst().map(Image::getUrl).orElse(null);
        if (!images.isEmpty()){
            return images.get(0).getUrl();
        }
        else{
            return "";
        }
    }
}
