package com.nashtech.dshop_api.dto.requests.User;

import com.nashtech.dshop_api.data.entities.StatusType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UserGetRequest {
    private String username;
    private String role;
    // private StatusType onlineStatus;
    private Boolean enableStatus;
}
