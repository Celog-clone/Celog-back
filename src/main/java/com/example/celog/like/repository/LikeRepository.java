package com.example.celog.like.repository;

import com.example.celog.like.entity.Like;
import com.example.celog.member.entity.Member;
import com.example.celog.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostAndMember(Post post, Member member);
    Optional<List<Like>> findByPost(Post post);
}
