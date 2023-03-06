package com.example.celog.post.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.celog.comment.dto.CommentResponseDto;
import com.example.celog.comment.entity.Comment;
import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.ResponseUtils;
import com.example.celog.common.SuccessResponse;
import com.example.celog.common.s3.FileUtil;
import com.example.celog.common.s3.Uploader;
import com.example.celog.enumclass.ExceptionEnum;
import com.example.celog.member.entity.Member;
import com.example.celog.member.repository.MemberRepository;
import com.example.celog.post.dto.PostRequestDto;
import com.example.celog.post.dto.PostResponseDtoWithComments;
import com.example.celog.post.dto.PostResponseDto;
import com.example.celog.post.entity.FileInfo;
import com.example.celog.post.entity.Post;
import com.example.celog.post.exception.CustomException;
import com.example.celog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;


import static com.example.celog.post.exception.Error.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {


    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    private final Uploader uploader;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 게시물 등록
    @Transactional
    public ApiResponseDto<PostResponseDto> addPost(PostRequestDto requestDto, Member member) throws IOException {
        log.info("2");
        String fileUrl = "";
        FileInfo fileInfo;
        MultipartFile file = requestDto.getFile();

        if (file.isEmpty()) {
            Post post = postRepository.save(Post.of(requestDto, member));
            return ResponseUtils.ok(PostResponseDto.from(post, member));
        }
        try {
            fileUrl = uploader.upload(file, "testImage");
            fileInfo = new FileInfo(
                    FileUtil.cutFileName(file.getOriginalFilename(), 500), fileUrl);
            requestDto.setImage(fileInfo.getFileUrl());
            requestDto.setOriginalFileName(file.getOriginalFilename());
        } catch (IOException ie) {
            log.info("S3파일 저장 중 예외 발생");
            throw ie;

        } catch (Exception e) {
            log.info("s3에 저장되었던 파일 삭제");
            uploader.delete(fileUrl.substring(fileUrl.lastIndexOf(".com/") + 5));
            throw e;
        }

        Post post = postRepository.save(Post.of(requestDto, member));
        return ResponseUtils.ok(PostResponseDto.from(post, member));
    }


    // 게시물 수정
    @Transactional
    public ApiResponseDto<PostResponseDto> modifyPost(PostRequestDto requestDto, Member member, Long id) throws IOException {

        String fileUrl = "";
        FileInfo fileInfo = new FileInfo(requestDto.getOriginalFileName(), requestDto.getImage());
        MultipartFile file = requestDto.getFile();
        Optional<Post> post = postRepository.findById(id);


        // 회원 X
        Member foundMember = memberRepository.findByIdAndId(id,member.getId()).orElseThrow(
                () ->  new CustomException(NOT_FOUND_MEMBER)
        );

        // 권한 X
        if(post.get().getId() != id)
            throw new CustomException(NO_AUTHORITY_MODIFY);

        // 기존 이미지 삭제
        FileInfo fileInfo1 = new FileInfo("삭제될 이미지", post.get().getImage());
        if (!(fileInfo1.getFileUrl() == null)) {
            uploader.delete(fileInfo1.S3key());
        }

        //이미지 비어있을시
        if (file.isEmpty()) {
            post.get().update(requestDto, member);
            return ResponseUtils.ok(PostResponseDto.from(post.get(), member));
        }
        // 게시글 id 와 사용자 정보 일치한다면, 게시글 수정

        fileUrl = uploader.upload(file, "testImage");
        fileInfo = new FileInfo(
                FileUtil.cutFileName(Objects.requireNonNull(file.getOriginalFilename()), 500), fileUrl);

        requestDto.setImage(fileInfo.getFileUrl());
        post.get().update(requestDto, member);

        return  ResponseUtils.ok(PostResponseDto.from(post.get(), member));

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
        if (post.getImage() == null) {
            postRepository.deleteById(id);
            return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "삭제 성공"));
        }
        FileInfo fileInfo = new FileInfo(post.getOriginalFilename(), post.getImage());
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

        for(Post post : foundPostList)
            responseDtoList.add(PostResponseDto.from(post, post.getMember()));

        return ResponseUtils.ok(responseDtoList);
    }

    // 게시물 상세보기
    @Transactional(readOnly = true)
    public ApiResponseDto<PostResponseDtoWithComments> findPost(Long id){

        // 게시물 X
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_POST)
        );
        List<CommentResponseDto> list = new ArrayList<>();
        for(Comment comment : post.getComment()){
            list.add(CommentResponseDto.from(comment));
        }

        PostResponseDtoWithComments responseDto = PostResponseDtoWithComments.from(post, post.getMember(), list);
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
            responseDtoList.add(PostResponseDto.from(post, post.getMember()));
        return ResponseUtils.ok(responseDtoList);
    }
}
