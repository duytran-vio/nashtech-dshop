package com.nashtech.dshop_api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.nashtech.dshop_api.data.entities.StatusType;
import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.data.entities.User_;
import com.nashtech.dshop_api.data.repositories.UserRepository;
import com.nashtech.dshop_api.dto.requests.User.UserGetRequest;
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

    public static Specification<User> likeUsername(String username) {
        return (root, query, builder) -> {
            return builder.like(root.get(User_.USERNAME), "%" + username + "%");
        };
    }

    public static Specification<User> hasOnlineStatus(StatusType onlineStatus) {
        return (root, query, builder) -> {
            return builder.equal(root.get(User_.ONLINE_STATUS), onlineStatus);
        };
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Page<UserDto> getAllUsers(UserGetRequest request, Pageable pageable) {
        Specification<User> spec = Specification.where(null);
        if (request.getUsername() != null) {
            spec = spec.and(likeUsername(request.getUsername()));
        }
        if (request.getOnlineStatus() != null) {
            spec = spec.and(hasOnlineStatus(request.getOnlineStatus()));
        }

        var users = userRepository.findAll(spec, pageable)
                                    .map(mapper::toDto);

        return users;
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
