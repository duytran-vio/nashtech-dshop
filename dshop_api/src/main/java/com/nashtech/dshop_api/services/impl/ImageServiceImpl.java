package com.nashtech.dshop_api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nashtech.dshop_api.data.entities.Image;
import com.nashtech.dshop_api.data.entities.Image_;
import com.nashtech.dshop_api.data.repositories.ImageRepository;
import com.nashtech.dshop_api.dto.responses.ImageUploadResponse;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException;
import com.nashtech.dshop_api.mappers.ImageMapper;
import com.nashtech.dshop_api.services.ImageService;
import com.nashtech.dshop_api.utils.FileUtils;

@Service
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.static-dir}")
    private String staticDir;

    private ImageRepository imageRepository;
    private ImageMapper mapper;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, ImageMapper mapper) {
        this.imageRepository = imageRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public ImageUploadResponse saveImage(MultipartFile file, String serverHost) {
        String fileName = file.getOriginalFilename();
        String saveDir = staticDir + uploadDir;
        String encodeFileName = FileUtils.saveFile(saveDir, fileName, file);
        String url = String.join("/", serverHost, uploadDir, encodeFileName);

        Image image = new Image();
        image.setFileName(fileName);
        image.setUrl(url);
        image.setSize(file.getSize());

        return mapper.toImageUploadResponse(imageRepository.save(image));
    }

    @Override
    public Image getEntityById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Image.class.getSimpleName(), Image_.ID, id));
    }
}
