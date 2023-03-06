package com.example.celog.comment.entity;

import com.example.celog.comment.dto.CommentRequestDto;
import com.example.celog.member.Entity.Member;
import com.example.celog.post.entity.Post;
import com.example.celog.superclass.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 500)
    private String comments;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    private Comment(CommentRequestDto commentRequestDto, Member member, Post post) {
        comments = commentRequestDto.getComments();
        this.member = member;
        this.post = post;
    }

    public static Comment of(CommentRequestDto commentRequestDto, Member member, Post post) {
        return Comment.builder()
                .commentRequestDto(commentRequestDto)
                .member(member)
                .post(post)
                .build();
    }

    public void update(CommentRequestDto commentRequestDto, Member member) {
        comments = commentRequestDto.getComments();
        this.member = member;
    }

}
