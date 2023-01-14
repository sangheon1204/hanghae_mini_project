package com.sparta.miniproject1.reply.repository;

import com.sparta.miniproject1.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<List<Reply>> findByComment_IdOrderByCreatedAtDesc(Long commentId);
}
