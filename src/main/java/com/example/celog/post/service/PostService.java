package com.example.celog.post.service;

import com.example.celog.comment.dto.CommentNoNicknameResponseDto;
import com.example.celog.comment.entity.Comment;
import com.example.celog.comment.repository.CommentRepository;
import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.ResponseUtils;
import com.example.celog.common.SuccessResponse;
import com.example.celog.common.s3.FileUtil;
import com.example.celog.common.s3.Uploader;
import com.example.celog.exception.CustomException;
import com.example.celog.like.entity.Like;
import com.example.celog.like.repository.LikeRepository;
import com.example.celog.member.entity.Member;
import com.example.celog.post.dto.*;
import com.example.celog.post.entity.FileInfo;
import com.example.celog.post.entity.Post;
import com.example.celog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.celog.exception.enumclass.Error.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {


    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final LikeRepository likeRepository;

    private final Uploader uploader;


    // 게시물 등록
    @Transactional
    public ApiResponseDto<PostAddResponseDto> addPost(PostRequestDto requestDto, Member member){

        String fileUrl = "";
        FileInfo fileInfo;
        MultipartFile file = requestDto.getImage();

        if (file.isEmpty()) {
            Post post = postRepository.save(Post.of(requestDto, member));
            return ResponseUtils.ok(PostAddResponseDto.from(post, member));
        }
        try {
            fileUrl = uploader.upload(file, "testImage");
            fileInfo = new FileInfo(
                    FileUtil.cutFileName(file.getOriginalFilename(), 500), fileUrl);
            requestDto.setUrl(fileInfo.getFileUrl());
            requestDto.setOriginalFileName(file.getOriginalFilename());
        } catch (IOException ie) {
            throw new CustomException(FAIL_S3_SAVE);

        } catch (Exception e) {
            uploader.delete(fileUrl.substring(fileUrl.lastIndexOf(".com/") + 5));
            throw new CustomException(DELETE_S3_FILE);
        }

        Post post = postRepository.save(Post.of(requestDto, member));
        return ResponseUtils.ok(PostAddResponseDto.from(post, member));
    }


    // 게시물 수정
    @Transactional
    public ApiResponseDto<PostSubResponseDto> modifyPost(PostRequestDto requestDto, Member member, Long id) {

        String fileUrl = "";
        FileInfo fileInfo = new FileInfo(requestDto.getOriginalFileName(), requestDto.getUrl());
        MultipartFile file = requestDto.getImage();
        Optional<Post> post = postRepository.findById(id);

        // 권한 X
        if(!post.get().getMember().getId().equals(member.getId()))
            throw new CustomException(NO_AUTHORITY);

        // 기존 이미지 삭제
        FileInfo fileInfo1 = new FileInfo("삭제될 이미지", post.get().getUrl());
        if (!(fileInfo1.getFileUrl() == null)) {
            uploader.delete(fileInfo1.S3key());
        }

        //이미지 비어있을시
        if (file.isEmpty()) {
            post.get().update(requestDto, member);
            return ResponseUtils.ok(PostSubResponseDto.from(post.get(), member));
        }
        // 게시글 id 와 사용자 정보 일치한다면, 게시글 수정

        try {
            fileUrl = uploader.upload(file, "testImage");
        } catch (IOException e) {
            throw new CustomException(FAIL_S3_SAVE);
        }
        fileInfo = new FileInfo(
                FileUtil.cutFileName(Objects.requireNonNull(file.getOriginalFilename()), 500), fileUrl);

        requestDto.setUrl(fileInfo.getFileUrl());
        requestDto.setOriginalFileName(fileInfo.getFileOriginName());
        post.get().update(requestDto, member);

        return  ResponseUtils.ok(PostSubResponseDto.from(post.get(), member));

    }

    // 게시물 삭제
    @Transactional
    public ApiResponseDto<SuccessResponse> deletePost(Member member, Long id) {
        // 선택한 게시글이 DB에 있는지 확인
        Optional<Post> found = postRepository.findById(id);
        if (found.isEmpty()) {
            throw new CustomException(NOT_FOUND_POST);
        }

        // 선택한 게시글의 작성자와 토큰에서 가져온 사용자 정보가 일치하는지 확인 (삭제하려는 사용자가 관리자라면 게시글 삭제 가능)
        Optional<Post> board = postRepository.findByIdAndMember(id, member);
        if (board.isEmpty()) { // 일치하는 게시물이 없다면
            throw new CustomException(NOT_FOUND_POST);
        }
        // S3 이미지 삭제

        Post post = postRepository
                .findById(id).orElseThrow(() -> new RuntimeException("존재 하지 않는 파일"));
        if (post.getUrl() == null) {
            postRepository.deleteById(id);
            return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "삭제 성공"));
        }
        FileInfo fileInfo = new FileInfo(post.getOriginalFilename(), post.getUrl());
        uploader.delete(fileInfo.S3key());

        // 게시글 id 와 사용자 정보 일치한다면, 게시글 수정
        postRepository.deleteById(id);
        log.info("1");
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "삭제 성공"));
    }

    // 게시물 전체 조회
    @Transactional(readOnly = true)
    public ApiResponseDto<List<PostResponseDto>> findPostList(){

        List<Post> foundPostList = postRepository.findAll();

        List<PostResponseDto> responseDtoList = new ArrayList<>();



        for(Post post : foundPostList){
            List<Like> likeCount = likeRepository.findByPost(post).orElseThrow(
                    () -> new CustomException(NOT_FOUND_POST)
            );
            responseDtoList.add(PostResponseDto.from(post, post.getMember(), likeCount.size()));
        }

        return ResponseUtils.ok(responseDtoList);
    }

    // 게시물 상세보기
    @Transactional(readOnly = true)
    public ApiResponseDto<PostResponseDtoWithComments> findPost(Long id){

        // 게시물 X
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_POST)
        );
        List<Comment> commentList = commentRepository.findAllByPostIdOrderByModifiedAtDesc(id);

        List<CommentNoNicknameResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            Member member = commentRepository.findById(comment.getId()).orElseThrow().getMember();
            commentResponseDtoList.add(CommentNoNicknameResponseDto.from(comment, member));
        }

        PostResponseDtoWithComments responseDto = PostResponseDtoWithComments.from(post, post.getMember(), commentResponseDtoList);
        return ResponseUtils.ok(responseDto);
    }

    // 게시물 검색 조회
    @Transactional(readOnly = true)
    public ApiResponseDto<List<PostResponseDto>> searchPost(String name){

        List<Post> foundPostList= postRepository.findByTitleContaining(name).orElseThrow(
                () -> new CustomException(NOT_FOUND_SEARCH)
        );

        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for(Post post : foundPostList){
            List<Like> likeCount = likeRepository.findByPost(post).orElseThrow(
                    () -> new CustomException(NOT_FOUND_POST)
            );
            responseDtoList.add(PostResponseDto.from(post, post.getMember(), likeCount.size()));
        }

        return ResponseUtils.ok(responseDtoList);
    }
}
