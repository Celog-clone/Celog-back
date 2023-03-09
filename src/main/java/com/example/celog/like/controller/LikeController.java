package com.example.celog.like.controller;

import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.SuccessResponse;
import com.example.celog.like.service.LikeService;
import com.example.celog.auth.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/posts/{id}/like")
    public ApiResponseDto<SuccessResponse> likeSave(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.saveLike(id, userDetails.getUser());
    }

}
