package com.nashtech.dshop_api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.dshop_api.data.entities.CustomerInfo;
import com.nashtech.dshop_api.data.entities.Role;
import com.nashtech.dshop_api.data.entities.Role_;
import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.data.repositories.RoleRepository;
import com.nashtech.dshop_api.dto.requests.User.UserCreateRequest;
import com.nashtech.dshop_api.dto.requests.User.UserLoginRequest;
import com.nashtech.dshop_api.dto.responses.LoginResponse;
import com.nashtech.dshop_api.dto.responses.UserDto;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException;
import com.nashtech.dshop_api.mappers.UserMapper;
import com.nashtech.dshop_api.security.JwtProvider;
import com.nashtech.dshop_api.services.AuthService;
import com.nashtech.dshop_api.services.CustomerInfoService;
import com.nashtech.dshop_api.services.UserService;


@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService{

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private UserMapper mapper;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private RoleRepository roleRepository;
    private CustomerInfoService customerInfoService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, 
                            JwtProvider jwtProvider,
                            UserMapper mapper,
                            PasswordEncoder passwordEncoder,
                            UserService userService,
                            RoleRepository roleRepository,
                            CustomerInfoService customerInfoService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.customerInfoService = customerInfoService;
    }

    @Override
    @Transactional
    public LoginResponse login(UserLoginRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        var authentication = authenticationManager.authenticate(usernamePassword);
        var accessToken = jwtProvider.generateAccessToken((User) authentication.getPrincipal());

        UserDto useDto = mapper.toDto((User) authentication.getPrincipal());
        LoginResponse response = new LoginResponse(useDto, accessToken);
        return response;
    }

    @Override
    @Transactional
    public UserDto register(UserCreateRequest request) {
        User user = mapper.toEntityFromCreateRequest(request);
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException(Role.class.getSimpleName(), Role_.ID, request.getRoleId()));
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setFirstName(request.getFirstName());
        customerInfo.setLastName(request.getLastName());
        customerInfo.setFullName(request.getFirstName() + " " + request.getLastName());

        user.setInfo(customerInfo);
        customerInfo.setUser(user);
        return mapper.toDto(userService.save(user));
    }
    
}
