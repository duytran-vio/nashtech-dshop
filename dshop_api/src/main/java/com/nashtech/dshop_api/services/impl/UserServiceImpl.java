package com.nashtech.dshop_api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.data.repositories.UserRepository;
import com.nashtech.dshop_api.dto.requests.UserCreateRequest;
import com.nashtech.dshop_api.dto.responses.UserDto;
import com.nashtech.dshop_api.mappers.UserMapper;
import com.nashtech.dshop_api.services.UserService;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private UserMapper mapper;

    
    public UserServiceImpl(@Autowired UserRepository userRepository,@Autowired UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDto createUser(UserCreateRequest userDto) {
        User user = mapper.toEntity(userDto);
        // System.out.println(user.toString());
        userRepository.save(user);
        return mapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                            .stream()
                            .map(mapper::toDto)
                            .toList();
    }
}