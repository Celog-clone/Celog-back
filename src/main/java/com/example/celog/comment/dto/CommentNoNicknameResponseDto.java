package com.example.celog.comment.dto;

import com.example.celog.comment.entity.Comment;
import com.example.celog.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentNoNicknameResponseDto {

    private Long id;
    private String comments;
    private String comment_nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private CommentNoNicknameResponseDto(Comment comment, Member member) {
        id = comment.getId();
        comments = comment.getComments();
        comment_nickname = member.getNickname();
        createAt = comment.getCreatedAt();
        modifiedAt = comment.getModifiedAt();
    }

    public static CommentNoNicknameResponseDto from(Comment comment, Member member) {
        return CommentNoNicknameResponseDto.builder()
                .comment(comment)
                .member(member)
                .build();
    }

}
