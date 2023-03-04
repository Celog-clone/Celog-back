package com.example.celog.like.entity;

import com.example.celog.like.dto.LikeRequestDto;
import com.example.celog.like.dto.LikeResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long likeCount;

    @Builder
    private Like(LikeRequestDto likeRequestDto) {
        likeCount = likeRequestDto.getLikeCount();
    }

    public static Like of(LikeRequestDto likeRequestDto) {
        return Like.builder()
                .likeRequestDto(likeRequestDto)
                .build();
    }

}
