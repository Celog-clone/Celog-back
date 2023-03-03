package com.example.celog.comment.entity;

import com.example.celog.comment.dto.request.CommentRequestDto;
import com.example.celog.superclass.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "내용을 적어주세요")
    private String comments;

//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;

    @Builder
    private Comment(CommentRequestDto commentRequestDto) {
        comments = commentRequestDto.getComments();
    }

    public static Comment of(CommentRequestDto commentRequestDto) {
        return Comment.builder()
                .commentRequestDto(commentRequestDto)
                .build();
    }

}
