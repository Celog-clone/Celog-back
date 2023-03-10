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
public class PostResponseDto {

    private Long id;

    private String title;

    private String contents;

    private String image;

    private String nickname;

    private int commentsCount;

    private int likeCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private PostResponseDto(Post post, Member member, int commentsCount, int likeCount) {
        id = post.getId();
        title = post.getTitle();
        contents = post.getContents();
        image = post.getUrl();
        nickname = member.getNickname();
        this.commentsCount = commentsCount;
        this.likeCount = likeCount;
        createdAt = post.getCreatedAt();
        modifiedAt = post.getModifiedAt();
    }

    public static PostResponseDto from(Post post, Member member, int likeCount) {
        return PostResponseDto.builder()
                .post(post)
                .member(member)
                .commentsCount(post.getComment().size())
                .likeCount(likeCount)
                .build();
    }
}