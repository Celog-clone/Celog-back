package com.example.celog.comment.service;

import com.example.celog.comment.dto.CommentRequestDto;
import com.example.celog.comment.dto.CommentResponseDto;
import com.example.celog.comment.entity.Comment;
import com.example.celog.comment.repository.CommentRepository;
import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.ResponseUtils;
import com.example.celog.common.SuccessResponse;
import com.example.celog.member.entity.Member;
import com.example.celog.post.entity.Post;
import com.example.celog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public ApiResponseDto<CommentResponseDto> saveComment(Long id, CommentRequestDto commentRequestDto, Member member) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("등록된 게시글이 없습니다.")
        );

        // 댓글 작성 / 작성된 댓글 db에 저장
        return ResponseUtils.ok(CommentResponseDto.from(commentRepository.save(Comment.of(commentRequestDto, member, post))));
    }

    public ApiResponseDto<CommentResponseDto> modifyComment(Long id, Long comment_id, CommentRequestDto commentRequestDto, Member member) {

        postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("등록된 게시글이 없습니다.")
        );


        Comment comment = foundMember(comment_id, member);

        comment.update(commentRequestDto, member);
        commentRepository.flush();

        return ResponseUtils.ok(CommentResponseDto.from(comment));
    }


    public ApiResponseDto<SuccessResponse> removeComment(Long id, Long comment_id, Member member) {

        foundMember(comment_id, member);

        commentRepository.deleteById(comment_id);
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "댓글 삭제 완료"));
    }

    private Comment foundMember(Long comment_id, Member member) {
        Member found = commentRepository.findById(comment_id).get().getMember();
        if (!member.getId().equals(found.getId())) {
            throw new IllegalArgumentException("자신이 작성한 댓글만 수정/삭제가 가능합니다.");
        }

        return commentRepository.findById(comment_id).orElseThrow(
                () -> new NullPointerException("댓글이 없습니다.")
        );
    }
}
