package com.hcmut.travogue.file;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(String folder, MultipartFile multipartFile) throws IOException {
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString(), "folder", folder))
                .get("secure_url")
                .toString();
    }

    public String uploadVideo(String folder, MultipartFile video) throws IOException {
        return cloudinary.uploader()
                .uploadLarge(video.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString(), "folder", folder, "resource_type", "video", "chunk_size", 3000000))
                .get("secure_url")
                .toString();
    }
}
