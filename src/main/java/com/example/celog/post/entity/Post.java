package com.example.celog.post.entity;

import com.example.celog.comment.entity.Comment;
import com.example.celog.like.entity.Like;
import com.example.celog.member.entity.Member;
import com.example.celog.post.dto.PostRequestDto;
import com.example.celog.superclass.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends Timestamped{
    @Id
    @Column(name = "post_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column()
    private String url;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column()
    private String originalFilename;

    @OneToMany(mappedBy = "post", cascade = REMOVE)
    private final List<Comment> comment = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = REMOVE)
    private final List<Like> likeList = new ArrayList<>();

    @Builder
    private Post(PostRequestDto postRequestDto, Member member) {
        title = postRequestDto.getTitle();
        contents = postRequestDto.getContents();
        url = postRequestDto.getUrl();
        originalFilename = postRequestDto.getOriginalFileName();
        this.member = member;
    }

    public static Post of(PostRequestDto postRequestDto, Member member) {
        return Post.builder()
                .postRequestDto(postRequestDto)
                .member(member)
                .build();
    }

    public void update(PostRequestDto requestDto, Member member) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.url = requestDto.getUrl();
    }
}