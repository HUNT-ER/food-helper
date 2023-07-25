package com.boldyrev.foodhelper.repositories;

import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageS3Repository {

    String save(String bucket, String path, String downloadFileLink) throws IOException;

    String getDownloadLink(String bucket, String path);

    void update(String bucket, String path, String downloadFileLink);

    void delete(String bucket, String path);
}
