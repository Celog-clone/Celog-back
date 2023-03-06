package com.example.celog.like.service;

import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.ResponseUtils;
import com.example.celog.like.dto.LikeResponseDto;
import com.example.celog.like.entity.Like;
import com.example.celog.like.repository.LikeRepository;
import com.example.celog.member.Entity.Member;
import com.example.celog.post.entity.Post;
import com.example.celog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.celog.enumclass.ExceptionEnum.NOT_EXIST_POST;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public ApiResponseDto<LikeResponseDto> saveLike(Long id, Member member) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException(NOT_EXIST_POST.getMsg())
        );

        Optional<Like> found = likeRepository.findByPostAndMember(post, member);
        if (found.isEmpty()) {
            Like like = likeRepository.save(Like.of(post, member));
            return ResponseUtils.ok(LikeResponseDto.from(like, true, HttpStatus.OK.value(), "좋아요 성공"));
        } else {
            Like like = likeRepository.findByMember(member).orElseThrow(
                    () -> new NullPointerException("해당 게시글에 좋아요 내역이 없습니다.")
            );
            likeRepository.delete(found.get());
            likeRepository.flush();
            return ResponseUtils.ok(LikeResponseDto.from(like, false, HttpStatus.OK.value(), "좋아요 취소"));
        }
    }
}

