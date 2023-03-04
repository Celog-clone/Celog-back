package com.example.celog.like.service;

import com.example.celog.like.dto.LikeResponseDto;
import com.example.celog.like.entity.Like;
import com.example.celog.like.repository.LikeRepository;
import com.example.celog.member.repository.MemberRepository;
import com.example.celog.post.entity.Post;
import com.example.celog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public ResponseEntity<LikeResponseDto> saveLike(Long id, UserDetailsImpl userDetails) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new NullPointerException("게시글이 존재하지 않습니다.");
        }

        Optional<Like> found = likeRepository.findByPostAndMemberId(post, userDetails.getUser());
        if (found.isEmpty()) {
            Like like = Like.of(post, userDetails.getUser());
            likeRepository.save(like);
        } else {
            likeRepository.delete(found.get());
            likeRepository.flush();
        }

        return null;
    }
}
