package com.example.celog.post.dto;

import com.example.celog.comment.dto.CommentNoNicknameResponseDto;
import com.example.celog.member.entity.Member;
import com.example.celog.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDtoWithComments {

    private Long id;

    private String title;

    private String contents;

    private String image;

    private String nickname;

    private List<CommentNoNicknameResponseDto> commentList = new ArrayList<>();

    private Integer likeCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private PostResponseDtoWithComments(Post post, Member member, List<CommentNoNicknameResponseDto> commentList) {
        id = post.getId();
        title = post.getTitle();
        contents = post.getContents();
        image = post.getUrl();
        nickname = member.getNickname();
        likeCount = post.getLikeList() == null ? 0 : post.getLikeList().size();
        createdAt = post.getCreatedAt();
        modifiedAt = post.getModifiedAt();
        this.commentList = commentList;
    }

    public static PostResponseDtoWithComments from(Post post, Member member, List<CommentNoNicknameResponseDto> commentList) {
        return PostResponseDtoWithComments.builder()
                .post(post)
                .member(member)
                .commentList(commentList)
                .build();
    }
}