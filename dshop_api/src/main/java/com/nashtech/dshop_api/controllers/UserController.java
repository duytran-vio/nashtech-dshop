package com.nashtech.dshop_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.dshop_api.dto.requests.UserCreateRequest;
import com.nashtech.dshop_api.dto.responses.UserDto;
import com.nashtech.dshop_api.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createBook(@Valid @RequestBody UserCreateRequest dto) {
        return userService.createUser(dto);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

}
