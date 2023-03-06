package com.example.celog.common.s3;

import com.amazonaws.auth.policy.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface Uploader {
    String upload(MultipartFile multipartFile, String dirName) throws IOException;

    void delete(String key);

}