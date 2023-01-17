package com.sparta.miniproject1.comment.repository;

import com.sparta.miniproject1.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostIdAndIsReplyOrderByCreatedAtDesc(Long id, Boolean check);
    List<Comment> findAllByReferenceId(Long id);
}
