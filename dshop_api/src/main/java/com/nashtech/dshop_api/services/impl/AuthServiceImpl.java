package com.nashtech.dshop_api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.dto.requests.User.UserLoginRequest;
import com.nashtech.dshop_api.dto.responses.LoginResponse;
import com.nashtech.dshop_api.dto.responses.UserDto;
import com.nashtech.dshop_api.mappers.UserMapper;
import com.nashtech.dshop_api.security.JwtProvider;
import com.nashtech.dshop_api.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private UserMapper mapper;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, 
                            JwtProvider jwtProvider,
                            UserMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.mapper = mapper;
    }

    @Override
    public LoginResponse login(UserLoginRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        var authentication = authenticationManager.authenticate(usernamePassword);
        var accessToken = jwtProvider.generateAccessToken((User) authentication.getPrincipal());

        UserDto useDto = mapper.toDto((User) authentication.getPrincipal());
        LoginResponse response = new LoginResponse(useDto, accessToken);
        return response;
    }
    
}
