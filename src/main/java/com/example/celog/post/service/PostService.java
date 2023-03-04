package com.example.celog.post.service;

import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.ResponseUtils;
import com.example.celog.common.SuccessResponse;
import com.example.celog.post.dto.PostRequestDto;
import com.example.celog.post.dto.PostResponseDtoWithComments;
import com.example.celog.post.dto.PostResponseDto;
import com.example.celog.post.entity.Member;
import com.example.celog.post.entity.Post;
import com.example.celog.post.exception.CustomException;
import com.example.celog.post.repository.MemberRepository;
import com.example.celog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.celog.post.exception.Error.*;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 게시물 등록
    @Transactional
    public ApiResponseDto<PostResponseDto> addPost(PostRequestDto requestDto, Long memberId){

        // 회원 X
        Member foundMember = memberRepository.findById(memberId).orElseThrow(
                () ->  new CustomException(NOT_FOUND_MEMBER)
        );

        Post post = Post.of(requestDto, foundMember);
        postRepository.save(post);

        return ResponseUtils.ok(PostResponseDto.from(post, foundMember.getNickname()));
    }

    // 게시물 수정
    @Transactional
    public ApiResponseDto<PostResponseDto> modifyPost(PostRequestDto requestDto, Long memberId, Long id){

        // 회원 X
        Member foundMember = memberRepository.findById(memberId).orElseThrow(
                () ->  new CustomException(NOT_FOUND_MEMBER)
        );

        // 게시물 X
        Post post = postRepository.findById(id).orElseThrow(
                () ->  new CustomException(NOT_FOUND_POST)
        );

        // 권한 X
        if(post.getMember().getId() != memberId)
            throw new CustomException(NO_AUTHORITY_MODIFY);

        post.update(requestDto.getTitle(), requestDto.getContents(), requestDto.getImage());
        return ResponseUtils.ok(PostResponseDto.from(post, foundMember.getNickname()));
    }

    // 게시물 삭제
    @Transactional
    public ApiResponseDto<SuccessResponse> deletePost(Long memberId, Long id) {
        // 회원 X
        Member foundMember = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        // 게시물 X
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_POST)
        );

        // 권한 X
        if (post.getMember().getId() != memberId)
            throw new CustomException(NO_AUTHORITY_DELETE);

        postRepository.delete(post);
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "삭제 완료"));
    }

    // 게시물 전체 조회
    @Transactional(readOnly = true)
    public ApiResponseDto<List<PostResponseDto>> findPostList(){

        List<Post> foundPostList = postRepository.findAll();
        if(foundPostList.isEmpty())
            throw new CustomException(NOT_FOUND_POST);
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for(Post post : foundPostList)
            responseDtoList.add(PostResponseDto.from(post, post.getMember().getNickname()));

        return ResponseUtils.ok(responseDtoList);
    }

    // 게시물 상세보기
    @Transactional(readOnly = true)
    public ApiResponseDto<PostResponseDtoWithComments> findPost(Long id){

        // 게시물 X
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_POST)
        );

        PostResponseDtoWithComments responseDto = PostResponseDtoWithComments.from(post, post.getMember().getNickname());
        return ResponseUtils.ok(responseDto);
    }

    // 게시물 검색 조회
    @Transactional(readOnly = true)
    public ApiResponseDto<List<PostResponseDto>> searchPost(String name){
        if(name.equals(""))
            throw new CustomException(NOT_FOUND_SEARCH);

        List<Post> foundPostList= postRepository.findByTitleContaining(name).orElseThrow(
                () -> new CustomException(NOT_FOUND_SEARCH)
        );


        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for(Post post : foundPostList)
            responseDtoList.add(PostResponseDto.from(post, post.getMember().getNickname()));
        return ResponseUtils.ok(responseDtoList);
    }
}
