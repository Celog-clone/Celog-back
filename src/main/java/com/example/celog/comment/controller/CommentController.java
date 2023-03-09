package com.example.celog.comment.controller;

import com.example.celog.comment.dto.CommentRequestDto;
import com.example.celog.comment.dto.CommentResponseDto;
import com.example.celog.comment.service.CommentService;
import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.SuccessResponse;
import com.example.celog.auth.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{id}/comments")
    public ApiResponseDto<CommentResponseDto> commentSave(@PathVariable Long id,
                                                          @Valid @RequestBody CommentRequestDto commentRequestDto,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.saveComment(id, commentRequestDto, userDetails.getUser());
    }

    @PutMapping("/posts/{id}/comments/{comment_id}")
    public ApiResponseDto<CommentResponseDto> commentModify(@PathVariable Long id,
                                                            @Valid @PathVariable Long comment_id,
                                                            @RequestBody CommentRequestDto commentRequestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.modifyComment(id, comment_id, commentRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/posts/{id}/comments/{comment_id}")
    public ApiResponseDto<SuccessResponse> commentRemove(@PathVariable Long id,
                                                         @PathVariable Long comment_id,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.removeComment(id, comment_id, userDetails.getUser());
    }

}
