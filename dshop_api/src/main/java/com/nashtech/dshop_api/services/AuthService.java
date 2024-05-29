package com.nashtech.dshop_api.services;

import com.nashtech.dshop_api.dto.requests.User.UserCreateRequest;
import com.nashtech.dshop_api.dto.requests.User.UserLoginRequest;
import com.nashtech.dshop_api.dto.responses.LoginResponse;
import com.nashtech.dshop_api.dto.responses.UserDto;

public interface AuthService {
    public LoginResponse login(UserLoginRequest request);
    public UserDto register(UserCreateRequest request);
}
