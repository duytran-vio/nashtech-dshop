package com.nashtech.dshop_api.dto.requests.User;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserUpdateRequest {
    private Boolean enableStatus;
}
