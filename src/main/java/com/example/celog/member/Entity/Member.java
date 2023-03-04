package com.example.celog.member.Entity;

import com.example.celog.comment.entity.Comment;
import com.example.celog.post.dto.PostRequestDto;
import com.example.celog.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @Column(name="member_id", nullable = false )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "member")
    List<Post> post = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    List<Comment> comment = new ArrayList<>();

    @Builder
    private Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public static Post of(PostRequestDto postRequestDto) {
        Post post = Post.builder().title(postRequestDto.getTitle()).contents(postRequestDto.getContents()).image(postRequestDto.getImage()).build();
        return post;
    }

}
