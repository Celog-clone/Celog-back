package com.example.celog.comment.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentRequestDto {

    @NotBlank(message = "댓글을 입력해주세요")
    private String comments;

}
