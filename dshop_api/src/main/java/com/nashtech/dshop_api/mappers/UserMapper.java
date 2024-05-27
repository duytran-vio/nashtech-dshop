package com.nashtech.dshop_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.dto.requests.UserCreateRequest;
import com.nashtech.dshop_api.dto.responses.UserDto;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    public abstract UserDto toDto(User user);
    
    @Mapping(source = "roleId", target = "role.id")
    @Mapping(target = "onlineStatus", constant = "INACTIVE")
    public abstract User toEntityFromCreateRequest(UserCreateRequest dto);
}
