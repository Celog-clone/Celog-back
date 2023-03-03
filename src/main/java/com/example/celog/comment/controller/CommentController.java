package com.example.celog.comment.controller;

import com.example.celog.comment.dto.request.CommentRequestDto;
import com.example.celog.comment.dto.response.CommentResponseDto;
import com.example.celog.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long id,
                                            @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.commentCreate(id, commentRequestDto);
    }




}
