package com.example.celog.common.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader implements Uploader{

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        log.info(multipartFile.getName());
        log.info(dirName);
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        log.info(String.valueOf(uploadFile));
        return upload(uploadFile, dirName);
    }

    @Override
    public void delete(String key) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + FileUtil.getRandomFileName(uploadFile.getName());
        log.info(fileName);
        String uploadImageUrl = putS3(uploadFile, fileName);
        log.info(uploadImageUrl);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일 삭제 성공");
        } else {
            log.info("파일 삭제");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        log.info("11111111111111111111111111111111111111111111111111111");
        log.info(file.getOriginalFilename());
        File convertFile = new File(file.getOriginalFilename());
        log.info("제발 되라");
        if(convertFile.createNewFile()) {
            log.info("88888888888");
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }
}