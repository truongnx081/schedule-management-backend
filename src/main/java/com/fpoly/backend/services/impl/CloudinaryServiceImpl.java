package com.fpoly.backend.services.impl;

import com.cloudinary.Cloudinary;
import com.fpoly.backend.dto.CloudinaryResponse;
import com.fpoly.backend.services.CloudinaryService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class CloudinaryServiceImpl implements CloudinaryService {

    Cloudinary cloudinary;

    @Override
    public CloudinaryResponse uploadFile(MultipartFile file, String fileName) {
        try {
            final Map result = this.cloudinary.uploader()
                    .upload(file.getBytes(),
                            Map.of("public_id", fileName));
            final String publicId = (String) result.get("public_id");

            // Chỉ lấy ID mà không cần lưu đường dẫn đầy đủ
            String[] publicIdParts = publicId.split("/");
            String idOnly = publicIdParts[publicIdParts.length - 1];

            return CloudinaryResponse.builder().publicId(idOnly).build();
        } catch (final Exception e) {
            throw new RuntimeException("Lỗi khi upload file: ", e);
        }
    }

    @Override
    public void deleteFile(String publicId) {
        try {
            cloudinary.uploader().destroy("student/" + publicId, Map.of());
        } catch (final Exception e) {
            throw new RuntimeException("Xóa không thành công với đường dẫn: " + publicId, e);
        }
    }
}
