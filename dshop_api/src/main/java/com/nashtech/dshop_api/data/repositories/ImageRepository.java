package com.nashtech.dshop_api.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nashtech.dshop_api.data.entities.Image;

public interface  ImageRepository extends JpaRepository<Image, Long>{
    
}
