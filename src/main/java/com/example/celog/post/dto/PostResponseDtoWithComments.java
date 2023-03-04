package com.example.celog.post.dto;

;
import com.example.celog.comment.entity.Comment;
import com.example.celog.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDtoWithComments {

    private Long id;

    private String title;

    private String contents;

    private String image;

    private String nickname;

    private List<Comment> commentList = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Builder
    private PostResponseDtoWithComments(Long id, String title, String contents, String image, String nickname, LocalDateTime createdAt, LocalDateTime modifiedAt, List<Comment> commentList) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.image = image;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.commentList = commentList;
    }

    public static PostResponseDtoWithComments from(Post post, String nickname) {
        PostResponseDtoWithComments postResponseDtoWithComments = PostResponseDtoWithComments.builder().id(post.getId()).title(post.getTitle()).contents(post.getContents()).image(post.getImage()).nickname(nickname).createdAt(post.getCreatedAt()).modifiedAt(post.getModifiedAt()).commentList(post.getComment()).build();
        return postResponseDtoWithComments;
    }
}
