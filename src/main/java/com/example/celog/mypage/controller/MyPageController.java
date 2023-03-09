package com.example.celog.mypage.controller;

import com.example.celog.common.ApiResponseDto;
import com.example.celog.mypage.dto.MyPageResponseDto;
import com.example.celog.mypage.service.MyPageService;
import com.example.celog.auth.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage")
    public ApiResponseDto<List<MyPageResponseDto>> myPostFindList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return myPageService.findMyPostList(userDetails.getUser());
    }

}
