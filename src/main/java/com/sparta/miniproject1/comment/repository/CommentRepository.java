package com.sparta.miniproject1.comment.repository;

import com.sparta.miniproject1.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findAllByPostIdAndIsReplyAndStateOrderByIdAsc(Long id, Boolean check, boolean state);
    Optional<List<Comment>> findAllByReferenceIdAndState(Long id, boolean state);
    Optional<List<Comment>> findAllByPostIdAndState(Long id, boolean state);
    Optional<List<Comment>> findByUserId(Long userId);
    Optional<Comment> findByIdAndState(Long id, boolean state);
}
