package com.nashtech.dshop_api.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nashtech.dshop_api.data.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    public Optional<Category> findByCategoryName(String categoryName);
    public boolean existsByCategoryName(String categoryName);
}
