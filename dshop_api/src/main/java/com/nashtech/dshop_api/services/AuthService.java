package com.nashtech.dshop_api.services;

import com.nashtech.dshop_api.dto.requests.User.UserLoginRequest;
import com.nashtech.dshop_api.dto.responses.LoginResponse;

public interface AuthService {
    public LoginResponse login(UserLoginRequest request);
    
}
