package com.sparta.miniproject1.reply.repository;

import com.sparta.miniproject1.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}