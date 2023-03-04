package com.example.celog.like.dto;

import com.example.celog.like.entity.Like;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeResponseDto {

    private Long id;
    private Long likeCount;

    @Builder
    private LikeResponseDto(Like like) {
        id = like.getId();
        likeCount = like.getLikeCount();
    }

    public static LikeResponseDto from(Like like) {
        return LikeResponseDto.builder()
                .like(like)
                .build();
    }
}
