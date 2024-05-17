package com.nashtech.dshop_api.data.repositories;

import com.nashtech.dshop_api.data.entities.User;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // User findByUsername(String username);
}