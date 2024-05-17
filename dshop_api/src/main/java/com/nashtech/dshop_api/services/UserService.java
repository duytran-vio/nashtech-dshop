package com.nashtech.dshop_api.services;

import java.util.List;

import com.nashtech.dshop_api.dto.requests.UserCreateRequest;
import com.nashtech.dshop_api.dto.responses.UserDto;

public interface UserService {
    public UserDto createUser(UserCreateRequest user);
    // public User getUser(String username);
    public UserDto getUserById(Long id);
    public List<UserDto> getAllUsers();
    // public void deleteUser(String username);
    // public void updateUser(User user);
}
