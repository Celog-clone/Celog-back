package com.example.celog.post.entity;

import com.example.celog.post.dto.PostRequestDto;
import com.example.celog.superclass.TimeStamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeStamped {
    @Id
    @Column(name="comments_id", nullable = false )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    private Comment(String comments, Member member, Post post) {
        this.comments = comments;
        this.member = member;
        this.post = post;
    }

}
