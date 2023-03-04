package com.example.celog.comment.dto.response;

import com.example.celog.comment.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private String comments;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private CommentResponseDto(Comment comment) {
        id = comment.getId();
        comments = comment.getComments();
        createAt = comment.getCreatedAt();
        modifiedAt = comment.getModifiedAt();
    }

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .comment(comment)
                .build();
    }

}
