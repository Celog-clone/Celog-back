package com.example.celog.comment.repository;

import com.example.celog.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostIdOrderByModifiedAtDesc(Long id);

}
