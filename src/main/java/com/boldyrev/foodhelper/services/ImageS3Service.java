package com.boldyrev.foodhelper.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageS3Service {

    String save(String bucket, String path, MultipartFile imageFile);

    String getDownloadLink(String bucket, String path);

    void delete(String bucket, String path);
}
