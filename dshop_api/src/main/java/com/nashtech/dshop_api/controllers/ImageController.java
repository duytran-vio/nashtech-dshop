package com.nashtech.dshop_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nashtech.dshop_api.dto.responses.ImageUploadResponse;
import com.nashtech.dshop_api.services.ImageService;

@Controller
@RequestMapping("api/images")
public class ImageController {
    
    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<Object> uploadImage(@RequestParam("file") MultipartFile file) {
        String serverHost = ServletUriComponentsBuilder.fromCurrentRequestUri()
                                                       .replacePath(null)
                                                       .build()
                                                       .toUriString();
        ImageUploadResponse response = imageService.saveImage(file, serverHost);
        return ResponseEntity.status(HttpStatus.CREATED)
                            .body(response);
    }
}
