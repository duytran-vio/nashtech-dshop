package com.nashtech.dshop_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.dshop_api.dto.requests.User.UserCreateRequest;
import com.nashtech.dshop_api.dto.requests.User.UserGetRequest;
import com.nashtech.dshop_api.dto.requests.User.UserUpdateRequest;
import com.nashtech.dshop_api.dto.responses.UserDto;
import com.nashtech.dshop_api.services.UserService;
import com.nashtech.dshop_api.utils.Constant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getUsers(@Valid UserGetRequest request, Pageable pageable) {
        Page<UserDto> users = userService.getAllUsers(request, pageable);
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok()
                            .body(Constant.DELETE_SUCCESS_MSG);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest request) {
        UserDto user = userService.updateUser(id, request);
        return ResponseEntity.ok().body(user);
    }
}
