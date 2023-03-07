package com.example.celog.comment.dto;

import com.example.celog.comment.entity.Comment;
import com.example.celog.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private String comments;
    private String nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private CommentResponseDto(Comment comment, Member member) {
        id = comment.getId();
        comments = comment.getComments();
        nickname = member.getNickname();
        createAt = comment.getCreatedAt();
        modifiedAt = comment.getModifiedAt();
    }

    public static CommentResponseDto from(Comment comment, Member member) {
        return CommentResponseDto.builder()
                .comment(comment)
                .member(member)
                .build();
    }

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .comment(comment)
                .build();
    }

}
