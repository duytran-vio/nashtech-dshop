package com.nashtech.dshop_api.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.nashtech.dshop_api.exceptions.UploadFileFailedException;

public class FileUtils {

    public static String saveFile(String uploadDir, String fileName, MultipartFile file) {
        Path root = Paths.get(uploadDir);

        try {
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
            String fileCode = UUID.randomUUID().toString();
            Path filepath = root.resolve(fileCode + "-" + fileName);

            Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);
            return filepath.getFileName().toString();
        } catch (Exception e) {
            throw new UploadFileFailedException(fileName);
        }
    }
}
