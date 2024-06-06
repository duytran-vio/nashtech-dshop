package com.nashtech.dshop_api.dto.responses;

import com.nashtech.dshop_api.data.entities.StatusType;

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
    // private StatusType onlineStatus;
    private Boolean enableStatus;
    private String role;
    private String firstName;
    private String lastName;
    private String dateCreated;
    private String dateModified;
}
