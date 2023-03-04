package com.example.celog.comment.controller;

import com.example.celog.comment.dto.CommentRequestDto;
import com.example.celog.comment.dto.CommentResponseDto;
import com.example.celog.comment.service.CommentService;
import com.example.celog.common.SuccessResponse;
import com.example.celog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments/{id}")
    public ResponseEntity<List<CommentResponseDto>> commentList(@PathVariable Long id) {
        return commentService.listComment(id);
    }

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentResponseDto> commentSave(@PathVariable Long id,
                                                          @Valid @RequestBody CommentRequestDto commentRequestDto,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.saveComment(id, commentRequestDto, userDetails.getUser());
    }

    @PutMapping("/posts/{id}/comments/{comment_id}")
    public ResponseEntity<CommentResponseDto> commentModify(@PathVariable Long id,
                                                            @Valid @PathVariable Long comment_id,
                                                            @RequestBody CommentRequestDto commentRequestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.modifyComment(id, comment_id, commentRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/posts/{id}/comments/{comment_id}")
    public ResponseEntity<SuccessResponse> commentRemove(@PathVariable Long id,
                                                         @PathVariable Long comment_id,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.removeComment(id, comment_id, userDetails.getUser());
    }

}
