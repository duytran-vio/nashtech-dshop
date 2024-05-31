package com.nashtech.dshop_api.services;

import org.springframework.web.multipart.MultipartFile;

import com.nashtech.dshop_api.data.entities.Image;
import com.nashtech.dshop_api.dto.responses.ImageUploadResponse;

public interface ImageService {
    public ImageUploadResponse saveImage(MultipartFile file, String serverHost);
    public Image getEntityById(Long id);
}
