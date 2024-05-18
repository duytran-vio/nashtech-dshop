package com.nashtech.dshop_api.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.nashtech.dshop_api.data.entities.CustomerInfo;
import com.nashtech.dshop_api.dto.responses.CustomerInfo.CustomerInfoDto;

@Mapper(componentModel = "spring")
public interface CustomerInfoMapper {
    public CustomerInfo toEntity(CustomerInfoDto customerInfoDto);
    public CustomerInfoDto toDto(CustomerInfo customerInfo);

    public CustomerInfo putEntityFromDto(CustomerInfoDto customerInfoDto, 
                                    @MappingTarget CustomerInfo customerInfo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public CustomerInfo patchEntityFromDto(CustomerInfoDto customerInfoDto, 
                                    @MappingTarget CustomerInfo customerInfo);
}
