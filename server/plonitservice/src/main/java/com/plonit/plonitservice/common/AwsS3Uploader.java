package com.plonit.plonitservice.common;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@Component
@RequiredArgsConstructor
public class AwsS3Uploader {

//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    private final AmazonS3Client amazonS3Client;
//
//    public String uploadFile(MultipartFile multipartFile, String filePath) throws IOException{
//        File uploadFile = convert(multipartFile)
//                .orElseThrow(() -> new IllegalArgumentException("MultipartFile에서 File로 전환에 실패했습니다."));
//
//        return upload(uploadFile, filePath);
//    }
//
//    public String upload(File uploadFile, String filePath){
//        String fileName = filePath + "/" + UUID.randomUUID() + uploadFile.getName();
//        String uploadImageUrl = putS3(uploadFile, fileName);
//
//        // s3로 업로드 후 로컬 파일 삭제
//        removeNewFile(uploadFile);
//
//        return uploadImageUrl;
//    }
//
//    // S3로 업로드
//    private String putS3(File uploadFile, String fileName) {
//        amazonS3Client.putObject(
//                new PutObjectRequest(bucket, fileName, uploadFile)
//                        .withCannedAcl(CannedAccessControlList.PublicRead));
//        return amazonS3Client.getUrl(bucket, fileName).toString();
//    }
//
//    // 로컬에 저장된 이미지 지우기
//    private void removeNewFile(File targetFile) {
//        if (targetFile.delete()) {
//            log.info("File delete success");
//        } else {
//            log.info("File delete fail");
//        }
//    }
//
//    // 로컬에 파일 업로드
//    private Optional<File> convert(MultipartFile file) throws IOException {
//        File convertFile = new File(String.valueOf(UUID.randomUUID()));
////        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
//        if(convertFile.createNewFile()) {
//            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
//                fos.write(file.getBytes());
//            }
//            return Optional.of(convertFile);
//        }
//        return Optional.empty();
//    }
}
