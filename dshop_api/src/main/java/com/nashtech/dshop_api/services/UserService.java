package com.nashtech.dshop_api.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.dto.responses.UserDto;

public interface UserService extends UserDetailsService{
    public User save(User user);
    public UserDto getUserById(Long id);
    public List<UserDto> getAllUsers();
    public void deleteUser(Long id);
    public User getUserEntityById(Long id);
    public Boolean isUserExist(Long id);
}
