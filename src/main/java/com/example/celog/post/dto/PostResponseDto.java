package com.example.celog.post.dto;

import com.example.celog.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;

    private String title;

    private String contents;

    private String image;

    private String nickname;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Builder
    private PostResponseDto(Long id, String title, String contents, String image, String nickname, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.image = image;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static PostResponseDto from(Post post, String nickname) {
        PostResponseDto postResponseDto = PostResponseDto.builder().id(post.getId()).title(post.getTitle()).contents(post.getContents()).image(post.getImage()).nickname(nickname).createdAt(post.getCreatedAt()).modifiedAt(post.getModifiedAt()).build();
        return postResponseDto;
    }
}
