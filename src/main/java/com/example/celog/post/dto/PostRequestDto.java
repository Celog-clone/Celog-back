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

    private String url;

    private String originalFileName;
    private MultipartFile image;

    @Builder
    private PostRequestDto(String title, String contents, String url, String originalFileName) {
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.originalFileName = originalFileName;
    }

    public static PostRequestDto from(String title, String contents, String url, String originalFileName) {
        PostRequestDto postRequestDto = PostRequestDto.builder().title(title).contents(contents).url(url).originalFileName(originalFileName).build();
        return postRequestDto;
    }

}
