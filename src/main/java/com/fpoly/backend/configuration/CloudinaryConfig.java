package com.fpoly.backend.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        final Map<String,String> config =new HashMap<>();
        config.put("cloud_name","dc06mgef2");
        config.put("api_key","818531128891358");
        config.put("api_secret","Z92n5eT61mpQleBZcKE6aF9Z2FU");
        return  new Cloudinary(config);
    }
}
