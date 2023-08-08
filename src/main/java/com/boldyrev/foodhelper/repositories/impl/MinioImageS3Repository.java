package com.boldyrev.foodhelper.repositories.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.boldyrev.foodhelper.exceptions.ImageNotSavedException;
import com.boldyrev.foodhelper.repositories.ImageS3Repository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

@Component
public class MinioImageS3Repository implements ImageS3Repository {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final AmazonS3 s3Client;

    //todo переделать на аргументы командной строки

    @Autowired
    public MinioImageS3Repository(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String save(String bucket, String path, MultipartFile imageFile) {

        try {
            File image = convertToFile(imageFile);
            String pathForSave = path + image.getName();

            log.debug("Saving image in storage");

            s3Client.putObject(bucket, pathForSave, image);

            log.debug("Image successfully saved to path: {}", pathForSave);

            deleteTemporalImage(image);

            return pathForSave;
        } catch (IOException e) {
            throw new ImageNotSavedException(e.getMessage());
        }
    }

    @Override
    public String getDownloadLink(String bucket, String path) {
        return s3Client.generatePresignedUrl(bucket, path,
            new Date(new Date().getTime() + 3600000l)).toString();
    }

    @Override
    public void delete(String bucket, String path) {
        log.debug("Deleting image in path {}/{}", bucket, path);
        s3Client.deleteObject(bucket, path);
    }

    private File convertToFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + ".jpg";
        log.debug("Converting image: {}", fileName);

        try (ReadableByteChannel readableByteChannel = Channels.newChannel(file.getInputStream());
            FileChannel fileChannel = new FileOutputStream(fileName).getChannel()) {

            fileChannel.transferFrom(readableByteChannel, 0, Integer.MAX_VALUE);

            return new File(fileName);
        }
    }

    private void deleteTemporalImage(File file) {
        try {
            log.debug("Deleting temporal image file: {}", file.getAbsolutePath());
            Files.delete(Path.of(file.getAbsolutePath()));
        } catch (IOException e) {
            log.error("Temporal image file not deleted in path: {}, Cause: {}", file.getAbsolutePath(), e.getMessage());
        }
        log.debug("Temporal image file was deleted: {}", file.getAbsolutePath());
    }
}
