package com.boldyrev.foodhelper.services;

public interface ImageS3Service {

    String save(String bucket, String path, String downloadFileLink);

    String getDownloadLink(String bucket, String path);

    void update(String bucket, String path, String downloadFileLink);

    void delete(String bucket, String path);
}
