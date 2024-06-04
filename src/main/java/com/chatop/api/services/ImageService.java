package com.chatop.api.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.api.exceptions.UploadException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImageService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {

        verifyPicture(file);

        try {
            Map uploadMap = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            return uploadMap.get("url").toString();
        } catch (IOException e) {
            throw new UploadException("Failed of picture's upload: " + e.getMessage());
        }
    }

    // Validates the uploaded picture to ensure it's not null or empty.
    private void verifyPicture(MultipartFile picture) {
        if (picture == null || picture.isEmpty()) {
            throw new UploadException("No picture provided");
        }
    }

}
