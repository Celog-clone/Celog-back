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

import static javax.persistence.CascadeType.*;

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

    @Column(nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> comment = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = REMOVE)
    private List<Like> likeList = new ArrayList<>();

    @Builder    // 매개변수 PostResponseDto 아닌 이유 상훈님께 물어볼것
    private Post(String title, String contents, String image, Member member) {
        this.title = title;
        this.contents = contents;
        this.image = image;
        this.member = member;
    }

        // post객체 만들지 말고 바로 리턴
    public static Post of(PostRequestDto postRequestDto, Member member) {
        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .contents(postRequestDto.getContents())
                .image(postRequestDto.getImage())
                .member(member)
                .build();
        return post;
    }

    public void update(String title, String contents, String image) {
        this.title = title;
        this.contents = contents;
        this.image = image;
    }
}