package com.example.celog.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {

    private String title;

    private String contents;

    private String url;

    private String originalFileName;
    private MultipartFile image;

}
