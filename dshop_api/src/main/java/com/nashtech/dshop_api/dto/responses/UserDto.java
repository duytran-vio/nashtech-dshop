package com.nashtech.dshop_api.dto.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String email;
    // private Long infoId;
}
