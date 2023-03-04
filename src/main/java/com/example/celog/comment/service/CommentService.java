package com.example.celog.comment.service;

import com.example.celog.comment.dto.request.CommentRequestDto;
import com.example.celog.comment.dto.response.CommentResponseDto;
import com.example.celog.comment.entity.Comment;
import com.example.celog.comment.repository.CommentRepository;
import com.example.celog.common.SuccessResponse;
import com.example.celog.member.entity.Member;
import com.example.celog.member.repository.MemberRepository;
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

    @Transactional(readOnly = true)
    public ResponseEntity<List<CommentResponseDto>> listComment(Long id) {
        List<Comment> commentList = commentRepository.findAllById(id);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseDtoList.add(CommentResponseDto.from(comment));
        }
        return ResponseEntity.ok(commentResponseDtoList);
    }

    public ResponseEntity<CommentResponseDto> saveComment(Long id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {

        // 댓글 작성 / 작성된 댓글 db에 저장
        return ResponseEntity.ok(CommentResponseDto.from(commentRepository.save(Comment.of(commentRequestDto, userDetails.getMember()))));
    }

    public ResponseEntity<CommentResponseDto> modifyComment(Long id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {

        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new NullPointerException("댓글이 없습니다.");
        }

        return ResponseEntity.ok(CommentResponseDto.from(commentRepository.save(Comment.of(commentRequestDto, userDetails.getMember()))));
    }


    public ResponseEntity<SuccessResponse> removeComment(Long id, UserDetailsImpl userDetails) {

        Optional<Member> member = memberRepository.findById(userDetails.getMember().getId());
        if (!member.get().getId().equals(commentRepository.findById(id).get().getMember().getId())) {
            throw new IllegalArgumentException("자신이 작성한 댓글만 수정이 가능합니다.");
        }

        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new NullPointerException("댓글이 없습니다.");
        }

        commentRepository.deleteById(id);
        return ResponseEntity.ok(SuccessResponse.of(HttpStatus.OK, "댓글 삭제 완료"));
    }
}
