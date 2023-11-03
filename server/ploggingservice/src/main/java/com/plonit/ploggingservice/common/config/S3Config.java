package com.plonit.ploggingservice.common.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class S3Config {
    private String accessKey;
    private String secretKey;
    private String region;
    private final Environment env;
    public S3Config(Environment env) {
        this.env = env;
        this.accessKey = env.getProperty("cloud.aws.credentials.access-key");
        this.secretKey = env.getProperty("cloud.aws.credentials.secret-key");
        this.region = env.getProperty("cloud.aws.region.static");

    }
    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
