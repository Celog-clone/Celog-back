package com.example.celog.post.controller;

import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.SuccessResponse;
import com.example.celog.post.dto.*;
import com.example.celog.post.service.PostService;
import com.example.celog.auth.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    // 게시물 등록
    @PostMapping("/posts")
    public ApiResponseDto<PostAddResponseDto> postAdd(@AuthenticationPrincipal UserDetailsImpl userDetails, @ModelAttribute PostRequestDto requestDto){
        return postService.addPost(requestDto,userDetails.getUser());
    }

    // 게시물 수정
    @PatchMapping("/posts/{id}")
    public ApiResponseDto<PostSubResponseDto> postModify(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @ModelAttribute PostRequestDto requestDto) {
        return postService.modifyPost(requestDto, userDetails.getMember(), id);
    }

    // 게시물 전체 조회
    @GetMapping("/posts")
    public ApiResponseDto<List<PostResponseDto>> postFindList() {
        return postService.findPostList();
    }

    // 게시물 상세 조회
    @GetMapping("/posts/{id}")
    public ApiResponseDto<PostResponseDtoWithComments> postFind(@PathVariable Long id) {
        return postService.findPost(id);
    }

    // 게시물 삭제
    @DeleteMapping("/posts/{id}")
    public ApiResponseDto<SuccessResponse> postDelete(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        return postService.deletePost(userDetails.getMember(), id);
    }

    // 게시물 검색 조회
    @GetMapping("/posts/search")
    public ApiResponseDto<List<PostResponseDto>> postSearch(@RequestParam String name) {
        if (name.equals("")) {
            return postFindList();
        }
        return postService.searchPost(name);
    }


}
