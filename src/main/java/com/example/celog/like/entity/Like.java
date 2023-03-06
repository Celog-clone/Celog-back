package com.example.celog.like.entity;

import com.example.celog.member.Entity.Member;
import com.example.celog.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@Entity(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    private Like(Post post, Member member) {
        this.post = post;
        this.member = member;
    }

    public static Like of(Post post, Member member) {
        return Like.builder()
                .post(post)
                .member(member)
                .build();
    }

}
