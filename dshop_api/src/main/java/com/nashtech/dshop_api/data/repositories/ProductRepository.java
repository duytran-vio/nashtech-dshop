package com.nashtech.dshop_api.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.dshop_api.data.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    boolean existsByProductName(String productName);
}
