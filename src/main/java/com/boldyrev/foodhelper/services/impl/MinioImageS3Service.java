package com.boldyrev.foodhelper.services.impl;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.boldyrev.foodhelper.exceptions.ImageNotSavedException;
import com.boldyrev.foodhelper.repositories.ImageS3Repository;
import com.boldyrev.foodhelper.services.ImageS3Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MinioImageS3Service implements ImageS3Service {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final ImageS3Repository imageRepository;

    @Autowired
    public MinioImageS3Service(ImageS3Repository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public String save(String bucket, String path, String downloadFileLink) {

        try {
            log.debug("Saving image in {}/{}", bucket, path);
            return imageRepository.save(bucket, path, downloadFileLink);
        } catch (IOException e) {
            log.error("Failed to download image by link={}", downloadFileLink);
            throw new ImageNotSavedException(e.getMessage());
        }

    }

    @Override
    public String getDownloadLink(String bucket, String path) {
        log.debug("Getting image download link from minio storage {}/{}", bucket, path);
        return imageRepository.getDownloadLink(bucket, path);
    }

    @Override
    public void update(String bucket, String path, String downloadFileLink) {
        log.debug("Updating image");
        save(bucket, path, downloadFileLink);
    }

    @Override
    public void delete(String bucket, String path) {
        log.debug("Deleting image in path {}/{}", bucket, path);
        imageRepository.delete(bucket, path);
    }

}
