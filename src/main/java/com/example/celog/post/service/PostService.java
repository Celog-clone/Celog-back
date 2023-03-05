package com.example.celog.post.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.ResponseUtils;
import com.example.celog.common.SuccessResponse;
import com.example.celog.common.s3.FileUtil;
import com.example.celog.common.s3.Uploader;
import com.example.celog.member.repository.MemberRepository;
import com.example.celog.post.dto.PostRequestDto;
import com.example.celog.post.dto.PostResponseDtoWithComments;
import com.example.celog.post.dto.PostResponseDto;
import com.example.celog.member.Entity.Member;
import com.example.celog.post.entity.FileInfo;
import com.example.celog.post.entity.Post;
import com.example.celog.post.exception.CustomException;
import com.example.celog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;


import static com.example.celog.post.exception.Error.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    private final Uploader uploader;

    private final AmazonS3Client amazonS3Client;

    // 게시물 등록
    @Transactional
    public ApiResponseDto<PostResponseDto> addPost(PostRequestDto requestDto, Long memberId){

        // 회원 X
        Member foundMember = memberRepository.findById(memberId).orElseThrow(
                () ->  new CustomException(NOT_FOUND_MEMBER)
        );

        String fileUrl = "";
        FileInfo fileInfo;
        MultipartFile file = requestDto.getFile();

        try {
            String fileName = "test/" + FileUtil.getRandomFileName(file.getOriginalFilename());
            File convertFile = new File(file.getOriginalFilename());
            if (convertFile.createNewFile()) {
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(file.getBytes());
                }
            }
            try{
                amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, convertFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            }
            catch (Exception e){
                log.info(e.getMessage());
            }
            fileUrl = amazonS3Client.getUrl(bucket, fileName).toString();
            fileInfo = new FileInfo(
                    FileUtil.cutFileName(file.getOriginalFilename(), 500), fileUrl);
            requestDto.setImage(fileInfo.getFileUrl());
            requestDto.setOriginalFileName(file.getOriginalFilename());
        } catch (IOException ie) {
            log.info("S3파일 저장 중 예외 발생");
        }

        catch (Exception e) {
            log.info("s3에 저장되었던 파일 삭제");
            uploader.delete(fileUrl.substring(fileUrl.lastIndexOf(".com/") + 5));
            throw e;
        }


        Post post = Post.of(requestDto, foundMember);
        postRepository.save(post);
        return ResponseUtils.ok(PostResponseDto.from(post, foundMember.getNickname()));
    }

    // 게시물 수정
    @Transactional
    public ApiResponseDto<PostResponseDto> modifyPost(PostRequestDto requestDto, Long memberId, Long id) throws IOException {

        String fileUrl = "";
        FileInfo fileInfo;
        MultipartFile file = requestDto.getFile();


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




        fileUrl = uploader.upload(file, "testImage");

        fileInfo = new FileInfo(
                FileUtil.cutFileName(file.getOriginalFilename(), 500), fileUrl);

        requestDto.setImage(fileInfo.getFileUrl());

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

        //Post post = postRepository
        //        .findById(id).orElseThrow(() -> new RuntimeException("존재 하지 않는 파일"));
        //FileInfo fileInfo = new FileInfo(post.getOriginalFileName(), post.getImage());
        //uploader.delete(fileInfo.S3key());

        postRepository.delete(post);
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "삭제 완료"));
    }

    // 게시물 전체 조회
    @Transactional(readOnly = true)
    public ApiResponseDto<List<PostResponseDto>> findPostList(){

        List<Post> foundPostList = postRepository.findAll();

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
