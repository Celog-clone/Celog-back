package com.example.celog.comment.service;

import com.example.celog.comment.dto.request.CommentRequestDto;
import com.example.celog.comment.dto.response.CommentResponseDto;
import com.example.celog.comment.entity.Comment;
import com.example.celog.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;


    public ResponseEntity<CommentResponseDto> saveComment(Long id, CommentRequestDto commentRequestDto) {

        // 댓글 작성 / 작성된 댓글 db에 저장
        return ResponseEntity.ok(CommentResponseDto.from(commentRepository.save(Comment.of(commentRequestDto))));
    }

    public ResponseEntity<CommentResponseDto> modifyComment(Long id, CommentRequestDto commentRequestDto) {

        try {
            commentRepository.findById(id);
        } catch (NullPointerException e) {
            e.getMessage();
        }

        return null;
    }
}
