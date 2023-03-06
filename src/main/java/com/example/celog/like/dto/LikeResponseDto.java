package com.example.celog.like.dto;

import com.example.celog.like.entity.Like;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeResponseDto {

    private Long id;
    private boolean isMine;
    private int status;
    private String message;

    @Builder
    private LikeResponseDto(Like like, boolean isMine, int status, String message) {
        id = like.getId();
        this.isMine = isMine;
        this.status = status;
        this.message = message;
    }

    public static LikeResponseDto from(Like like, boolean isMine, int status, String message) {
        return LikeResponseDto.builder()
                .like(like)
                .isMine(isMine)
                .status(status)
                .message(message)
                .build();
    }
}
