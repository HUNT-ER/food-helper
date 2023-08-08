package com.boldyrev.foodhelper.repositories;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

public interface ImageS3Repository {

    String save(String bucket, String path, MultipartFile imageFile) throws IOException, AmazonS3Exception;

    String getDownloadLink(String bucket, String path) throws AmazonS3Exception;

    void delete(String bucket, String path) throws AmazonS3Exception;
}
