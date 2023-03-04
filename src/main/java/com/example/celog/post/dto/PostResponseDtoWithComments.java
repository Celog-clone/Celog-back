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

    private Integer likeCount;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Builder    // 매개변수 Post 아닌 이유?? 상훈님께 물어볼것
    private PostResponseDtoWithComments(Long id, String title, String contents, String image, String nickname, Integer likeCount, LocalDateTime createdAt, LocalDateTime modifiedAt, List<Comment> commentList) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.image = image;
        this.nickname = nickname;
        this.commentList = commentList;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static PostResponseDtoWithComments from(Post post, String nickname) {
        PostResponseDtoWithComments postResponseDtoWithComments = PostResponseDtoWithComments.builder().id(post.getId()).title(post.getTitle()).contents(post.getContents()).image(post.getImage()).nickname(nickname).createdAt(post.getCreatedAt()).modifiedAt(post.getModifiedAt()).commentList(post.getComment()).build();
        return postResponseDtoWithComments;
    }
}