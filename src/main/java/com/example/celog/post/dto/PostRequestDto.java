package com.example.celog.post.dto;

import lombok.Builder;
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

    private String image;

    private String originalFileName;
    private MultipartFile file;

    @Builder
    private PostRequestDto(String title, String contents, String image, String originalFileName) {
        this.title = title;
        this.contents = contents;
        this.image = image;
        this.originalFileName = originalFileName;
    }

    public static PostRequestDto from(String title, String contents, String image, String originalFileName) {
        PostRequestDto postRequestDto = PostRequestDto.builder().title(title).contents(contents).image(image).originalFileName(originalFileName).build();
        return postRequestDto;
    }

}
