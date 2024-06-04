package com.chatop.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

    private final String CLOUDINARY_URL = System.getenv("CLOUDINARY_URL");

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(CLOUDINARY_URL);
    }
}
