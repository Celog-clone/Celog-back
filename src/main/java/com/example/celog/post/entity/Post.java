package com.example.celog.post.entity;

import com.example.celog.post.dto.PostRequestDto;
import com.example.celog.superclass.TimeStamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends TimeStamped {
    @Id
    @Column(name = "post_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    private Post(String title, String contents, String image, Member member) {
        this.title = title;
        this.contents = contents;
        this.image = image;
        this.member = member;
    }

    public static Post of(PostRequestDto postRequestDto, Member member) {
        Post post = Post.builder().title(postRequestDto.getTitle()).contents(postRequestDto.getContents()).image(postRequestDto.getImage()).member(member).build();
        return post;
    }
    public void update(String title, String contents, String image) {
        this.title = title;
        this.contents = contents;
        this.image = image;
    }
}
