package com.example.celog.like.service;

import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.ResponseUtils;
import com.example.celog.common.SuccessResponse;
import com.example.celog.like.entity.Like;
import com.example.celog.like.repository.LikeRepository;
import com.example.celog.member.repository.MemberRepository;
import com.example.celog.post.entity.Post;
import com.example.celog.post.repository.PostRepository;
import com.example.celog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public ApiResponseDto<SuccessResponse> saveLike(Long id, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );

        Optional<Like> found = likeRepository.findByPostAndMember(post, userDetails.getMember());
        if (found.isEmpty()) {
            Like like = Like.of(post, userDetails.getMember());
            likeRepository.save(like);
            return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "좋아요 성공"));
        } else {
            likeRepository.delete(found.get());
            likeRepository.flush();
            return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "좋아요 취소"));
        }
    }
}
