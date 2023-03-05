package com.example.celog.post.controller;

import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.SuccessResponse;
import com.example.celog.post.dto.PostRequestDto;
import com.example.celog.post.dto.PostResponseDto;
import com.example.celog.post.dto.PostResponseDtoWithComments;
import com.example.celog.post.service.PostService;
import com.example.celog.security.UserDetailsImpl;
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
    public ApiResponseDto<PostResponseDto> postAdd(@RequestBody PostRequestDto requestDto,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.addPost(requestDto, userDetails.getUser());
    }

    // 게시물 수정
    @PatchMapping("/posts/{id}")
    public ApiResponseDto<PostResponseDto> postModify(@PathVariable Long id,
                                     @RequestBody PostRequestDto requestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.modifyPost(id, requestDto, userDetails.getUser());
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
    public ApiResponseDto<SuccessResponse> postDelete(@PathVariable Long id,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getUser());
    }

    // 게시물 검색 조회
    @GetMapping("/posts/search")
    public ApiResponseDto<List<PostResponseDto>> postSearch(@RequestParam String name) {
        return postService.searchPost(name);
    }


}
