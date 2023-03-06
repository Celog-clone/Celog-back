package com.example.celog.post.dto;

import com.example.celog.member.entity.Member;
import com.example.celog.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostSubResponseDto {

    private Long id;

    private String title;

    private String contents;

    private String image;

    private String nickname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;
    @Builder
    private PostSubResponseDto(Post post, Member member) {
        id = post.getId();
        title = post.getTitle();
        contents = post.getContents();
        image = post.getUrl();
        nickname = member.getNickname();
        createdAt = post.getCreatedAt();
        modifiedAt = post.getModifiedAt();
    }

    public static PostSubResponseDto from(Post post, Member member) {
        return PostSubResponseDto.builder()
                .post(post)
                .member(member)
                .build();
    }

}
