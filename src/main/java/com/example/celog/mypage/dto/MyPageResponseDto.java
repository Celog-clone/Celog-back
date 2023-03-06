package com.example.celog.mypage.dto;

import com.example.celog.member.Entity.Member;
import com.example.celog.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPageResponseDto {

    private Long id;
    private String title;
    private String contents;
    private String image;
    private String nickname;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    private MyPageResponseDto(Post post, Member member) {
        id = post.getId();
        title = post.getTitle();
        contents = post.getContents();
        image = post.getImage();
        nickname = member.getNickname();
        likeCount = post.getLikeList() == null ? 0 : post.getLikeList().size();
        createdAt = post.getCreatedAt();
        modifiedAt = post.getModifiedAt();
    }

    public static MyPageResponseDto from(Post post, Member member) {
        return MyPageResponseDto.builder()
                .post(post)
                .member(member)
                .build();
    }

}
