package com.example.celog.mypage.service;

import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.ResponseUtils;
import com.example.celog.member.Entity.Member;
import com.example.celog.mypage.dto.MyPageResponseDto;
import com.example.celog.post.entity.Post;
import com.example.celog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final PostRepository postRepository;

    public ApiResponseDto<List<MyPageResponseDto>> findMyPostList(Member member) {
        List<Post> postList = postRepository.findAllByMemberId(member.getId()).get();
        List<MyPageResponseDto> myPageResponseDtoList = new ArrayList<>();
        for (Post post : postList) {
            myPageResponseDtoList.add(MyPageResponseDto.from(post, member));
        }

        return ResponseUtils.ok(myPageResponseDtoList);
    }
}
