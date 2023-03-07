package com.example.celog.mypage.dto;

import com.example.celog.member.entity.Member;
import com.example.celog.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPageResponseDto {

    private final Long id;
    private final String title;
    private final String nickname;
    private final String contents;
    private final String image;
//    private final Integer likeCount;
    private final Integer commentsCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    private MyPageResponseDto(Post post, Member member) {
        id = post.getId();
        title = post.getTitle();
        nickname = member.getNickname();
        contents = post.getContents();
        image = post.getUrl();
//        likeCount = post.getLikeList() == null ? 0 : post.getLikeList().size();
        commentsCount = post.getComment() == null ? 0 : post.getComment().size();
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
