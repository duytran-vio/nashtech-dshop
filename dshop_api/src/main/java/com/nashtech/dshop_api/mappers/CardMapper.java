package com.nashtech.dshop_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.nashtech.dshop_api.data.entities.Card;
import com.nashtech.dshop_api.dto.responses.CustomerInfo.CardDto;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(source = "expiredDate", target = "expiredDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Card toEntity(CardDto cardDto);

    @Mapping(source = "expiredDate", target = "expiredDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    CardDto toDto(Card card);

    @Mapping(source = "expiredDate", target = "expiredDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Card updateEntityFromDto(@MappingTarget Card card, CardDto cardDto);
}
