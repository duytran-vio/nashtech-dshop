package com.nashtech.dshop_api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.data.repositories.UserRepository;
import com.nashtech.dshop_api.dto.responses.UserDto;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException;
import com.nashtech.dshop_api.mappers.UserMapper;
import com.nashtech.dshop_api.services.UserService;


@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private UserMapper mapper;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, 
                            UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                            .stream()
                            .map(mapper::toDto)
                            .toList();
    }

    @Override 
    public UserDto getUserById(Long id) {
        User user = this.getUserEntityById(id);
        return mapper.toDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = this.getUserEntityById(id);
        userRepository.delete(user);
    }

    @Override
    public User getUserEntityById(Long id) {
        return userRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException(User.class.getSimpleName(), "id", id));
    }

    @Override
    public Boolean isUserExist(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));  
        return user;
    }
}
