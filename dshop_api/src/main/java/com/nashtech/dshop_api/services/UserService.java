package com.nashtech.dshop_api.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.dto.requests.UserCreateRequest;
import com.nashtech.dshop_api.dto.requests.User.UserGetRequest;
import com.nashtech.dshop_api.dto.responses.UserDto;

import jakarta.validation.Valid;

public interface UserService {
    public UserDto createUser(UserCreateRequest user);
    public UserDto getUserById(Long id);
    public void deleteUser(Long id);
    public User getUserEntityById(Long id);
    public Boolean isUserExist(Long id);
    public Page<UserDto> getAllUsers(UserGetRequest request, Pageable pageable);
}
