package com.nashtech.dshop_api.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nashtech.dshop_api.data.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
