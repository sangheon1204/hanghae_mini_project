package com.sparta.miniproject1.reply.repository;

import com.sparta.miniproject1.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByCommentIdOrderByCreatedAtDesc(Long id);
    List<Reply> findALlByCommentId(Long id);
}
