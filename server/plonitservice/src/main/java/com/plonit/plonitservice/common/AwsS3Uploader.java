package com.plonit.plonitservice.common;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@Component
public class AwsS3Uploader {

    private String bucket;
    private final AmazonS3Client amazonS3Client;
    private final Environment env;

    public AwsS3Uploader(AmazonS3Client amazonS3Client, Environment env){
        this.amazonS3Client = amazonS3Client;
        this.env = env;
        this.bucket = env.getProperty("cloud.aws.s3.bucket");
    }

    public String uploadFile(MultipartFile multipartFile, String filePath) throws IOException{
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile에서 File로 전환에 실패했습니다."));

        return upload(uploadFile, filePath, multipartFile.getOriginalFilename(), multipartFile.getContentType());
    }

    public String upload(File uploadFile, String filePath, String originalName, String contentType){
        String fileName = filePath + "/" + UUID.randomUUID() + originalName;
        String uploadImageUrl = putS3(uploadFile, fileName, contentType);

        // s3로 업로드 후 로컬 파일 삭제
        removeNewFile(uploadFile);

        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName, String contentType) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentDisposition("inline");

        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withMetadata(metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
        } else {
            log.info("File delete fail");
        }
    }

    // 로컬에 파일 업로드
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(String.valueOf(UUID.randomUUID()));
//        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
