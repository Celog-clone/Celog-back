package com.example.celog.post.repository;

import com.example.celog.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<Post>> findByTitleContaining(String keyword);

    Optional<List<Post>> findAllByMemberId(Long memberId);
}