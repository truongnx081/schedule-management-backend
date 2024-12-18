package com.fpoly.backend.services;

import com.fpoly.backend.dto.CloudinaryResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    public CloudinaryResponse uploadFile(final MultipartFile file, final String fileName);

    public void deleteFile(String publicId);
}
