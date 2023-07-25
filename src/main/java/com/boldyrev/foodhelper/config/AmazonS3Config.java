package com.boldyrev.foodhelper.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:s3minio.properties")
public class AmazonS3Config {

    //todo Creds and Endpoint from commandline

    @Value("${s3.credentials.accesskey}")
    private String s3AccessKey;

    @Value("${s3.credentials.secretkey}")
    private String s3SecretKey;

    @Value("${s3.endpoint}")
    private String s3Endpoint;

    @Bean
    public AWSCredentials getAwsCredentials() {
        return new BasicAWSCredentials(s3AccessKey, s3SecretKey);
    }

    @Bean
    public EndpointConfiguration getEndpoint() {
        return new EndpointConfiguration(s3Endpoint, Regions.EU_WEST_1.getName());
    }

    @Bean
    public AmazonS3 getAmazonS3Client() {
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(getAwsCredentials()))
            .withEndpointConfiguration(getEndpoint())
            .build();
    }
}
