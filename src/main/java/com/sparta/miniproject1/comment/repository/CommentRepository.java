package com.sparta.miniproject1.comment.repository;

import com.sparta.miniproject1.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findByPost_IdOrderByCreatedAtDesc(Long postId);
}
