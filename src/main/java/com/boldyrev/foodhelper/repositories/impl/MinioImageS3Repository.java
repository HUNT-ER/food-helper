package com.boldyrev.foodhelper.repositories.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.boldyrev.foodhelper.repositories.ImageS3Repository;
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


public class MinioImageS3Repository implements ImageS3Repository {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final AmazonS3 s3Client;

    //todo переделать на аргументы командной строки

    @Autowired
    public MinioImageS3Repository(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String save(String bucket, String path, String downloadFileLink) {
        File image = downloadFile(downloadFileLink);
        String pathForSave = path + image.getName();

        log.debug("Saving image in storage");
        PutObjectResult result = s3Client.putObject(bucket, pathForSave, image);
        log.debug("Image successfully saved to path: {}", pathForSave);

        deleteTemporalImage(image);

        return pathForSave;
    }

    @Override
    public String getDownloadLink(String bucket, String path) {
        return s3Client.generatePresignedUrl(bucket, path,
            new Date(new Date().getTime() + 3600000l)).toString();
    }

    @Override
    public void update(String bucket, String path, String downloadFileLink) {
        log.debug("Updating image");
        save(bucket, path, downloadFileLink);
    }

    @Override
    public void delete(String bucket, String path) {
        log.debug("Deleting image in path {}/{}", bucket, path);
        s3Client.deleteObject(bucket, path);
    }

    private File downloadFile(String downloadFileLink) {
        ReadableByteChannel readableByteChannel = null;
        FileChannel fileChannel = null;
        String fileName = UUID.randomUUID() + ".jpeg";
        try {
            log.debug("Download image: {}", downloadFileLink);
            readableByteChannel = Channels.newChannel(new URL(downloadFileLink).openStream());
            fileChannel = new FileOutputStream(fileName).getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Integer.MAX_VALUE);
        } catch (IOException e) {
            log.debug("Unable to download image from link: {}", downloadFileLink);
        }
        return new File(fileName);
    }

    private void deleteTemporalImage(File file) {
        try {
            log.debug("Deleting temporal image file: {}", file.getAbsolutePath());
            Files.delete(Path.of(file.getAbsolutePath()));
        } catch (IOException e) {
            log.debug("Temporal image file not deleted in path: {}", file.getAbsolutePath());
        }
        log.debug("Temporal image file was deleted: {}", file.getAbsolutePath());
    }
}
