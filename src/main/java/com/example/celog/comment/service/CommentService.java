package com.example.celog.comment.service;

import com.example.celog.comment.dto.CommentRequestDto;
import com.example.celog.comment.dto.CommentResponseDto;
import com.example.celog.comment.entity.Comment;
import com.example.celog.comment.repository.CommentRepository;
import com.example.celog.common.SuccessResponse;
import com.example.celog.member.entity.Member;
import com.example.celog.member.repository.MemberRepository;
import com.example.celog.post.entity.Post;
import com.example.celog.post.repository.PostRepository;
import com.example.celog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<List<CommentResponseDto>> listComment(Long id) {
        List<Comment> commentList = commentRepository.findAllById(id);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseDtoList.add(CommentResponseDto.from(comment));
        }
        return ResponseEntity.ok(commentResponseDtoList);
    }

    public ResponseEntity<CommentResponseDto> saveComment(Long id, CommentRequestDto commentRequestDto, Member member) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("등록된 게시글이 없습니다.")
        );

        // 댓글 작성 / 작성된 댓글 db에 저장
        return ResponseEntity.ok(CommentResponseDto.from(commentRepository.save(Comment.of(commentRequestDto, member, post))));
    }

    public ResponseEntity<CommentResponseDto> modifyComment(Long id, Long comment_id, CommentRequestDto commentRequestDto, Member member) {

        postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("등록된 게시글이 없습니다.")
        );

        Member found = commentRepository.findById(comment_id).get().getMember();
        if (!member.getId().equals(found.getId())) {
            throw new IllegalArgumentException("자신이 작성한 댓글만 수정/삭제가 가능합니다.");
        }

        Optional<Comment> comment = commentRepository.findById(comment_id);
        if (comment.isEmpty()) {
            throw new NullPointerException("댓글이 없습니다.");
        }

        comment.get().update(commentRequestDto, member);
        commentRepository.flush();

        return ResponseEntity.ok(CommentResponseDto.from(comment.get()));
    }


    public ResponseEntity<SuccessResponse> removeComment(Long id, Long comment_id, Member member) {

        Member found = commentRepository.findById(comment_id).get().getMember();
        if (!member.getId().equals(found.getId())) {
            throw new IllegalArgumentException("자신이 작성한 댓글만 수정/삭제가 가능합니다.");
        }

        Optional<Comment> comment = commentRepository.findById(comment_id);
        if (comment.isEmpty()) {
            throw new NullPointerException("댓글이 없습니다.");
        }

        commentRepository.deleteById(comment_id);
        return ResponseEntity.ok(SuccessResponse.of(HttpStatus.OK, "댓글 삭제 완료"));
    }
}
