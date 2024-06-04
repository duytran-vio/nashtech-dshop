package com.nashtech.dshop_api.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.dto.requests.User.UserGetRequest;
import com.nashtech.dshop_api.dto.requests.User.UserUpdateRequest;
import com.nashtech.dshop_api.dto.responses.UserDto;

public interface UserService extends UserDetailsService{
    public User save(User user);
    public UserDto getUserById(Long id);
    public void deleteUser(Long id);
    public User getUserEntityById(Long id);
    public Boolean isUserExist(Long id);
    public Page<UserDto> getAllUsers(UserGetRequest request, Pageable pageable);
    public UserDto updateUser(Long id, UserUpdateRequest request);
}
