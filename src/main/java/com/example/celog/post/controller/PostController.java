package com.example.celog.post.controller;

import com.example.celog.common.ApiResponseDto;
import com.example.celog.post.dto.PostRequestDto;
import com.example.celog.post.dto.PostResponseDto;
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
    public ApiResponseDto postAdd(@RequestParam Long memberId, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.addPost(requestDto, memberId);
    }

    // 게시물 수정
    @PatchMapping("/posts/{id}")
    public ApiResponseDto postModify(@RequestParam Long memberId, @PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.modifyPost(requestDto, memberId, id);
    }

    // 게시물 전체 조회
    @GetMapping("/posts")
    public ApiResponseDto postFindList() {
        return postService.findPostList();
    }

    // 게시물 상세 조회
    @GetMapping("/posts/{id}")
    public ApiResponseDto postFind(@PathVariable Long id) {
        return postService.findPost(id);
    }

    // 게시물 삭제
    @DeleteMapping("/posts/{id}")
    public ApiResponseDto postDelete(@RequestParam Long memberId, @PathVariable Long id) {
        return postService.deletePost(memberId, id);
    }

    // 게시물 검색 조회
    @GetMapping("/posts/search")
    public ApiResponseDto<List<PostResponseDto>> postSearch(@RequestParam String name) {
        return postService.searchPost(name);
    }


}
