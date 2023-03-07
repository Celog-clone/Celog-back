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
public class PostLikeResponseDto {
    private Long id;

    private String title;

    private String contents;

    private String image;

    private String nickname;

    private int likeCount;

    private Integer commentsCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private PostLikeResponseDto(Post post, Member member, int likeCount) {
        id = post.getId();
        title = post.getTitle();
        contents = post.getContents();
        image = post.getUrl();
        nickname = member.getNickname();
        commentsCount = post.getComment() == null ? 0 : post.getComment().size();
        this.likeCount = likeCount;
        createdAt = post.getCreatedAt();
        modifiedAt = post.getModifiedAt();
    }

    public static PostLikeResponseDto from(Post post, Member member, int likeCount) {
        return PostLikeResponseDto.builder()
                .post(post)
                .member(member)
                .likeCount(likeCount)
                .build();
    }
}
