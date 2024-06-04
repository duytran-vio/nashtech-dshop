package com.nashtech.dshop_api.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.dto.requests.User.UserCreateRequest;
import com.nashtech.dshop_api.dto.requests.User.UserUpdateRequest;
import com.nashtech.dshop_api.dto.responses.UserDto;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Mapping(source = "role.roleName", target = "role")
    @Mapping(source = "enableStatus", target = "enableStatus")
    public abstract UserDto toDto(User user);
    
    @Mapping(source = "roleId", target = "role.id")
    @Mapping(target = "onlineStatus", constant = "INACTIVE")
    @Mapping(target = "enableStatus", constant = "true")
    public abstract User toEntityFromCreateRequest(UserCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract User toEntityFromUpdateRequest(UserUpdateRequest dto, @MappingTarget User user);
}
