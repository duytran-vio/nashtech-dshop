package com.nashtech.dshop_api.data.repositories;

import com.nashtech.dshop_api.data.entities.CustomerInfo;

import java.util.Optional;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long> {
    Optional<CustomerInfo> findByUserId(Long userId);
}