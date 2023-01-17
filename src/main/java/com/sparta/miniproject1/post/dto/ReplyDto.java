package com.sparta.miniproject1.post.dto;

import com.sparta.miniproject1.comment.entity.Comment;
import lombok.Getter;

@Getter
public class ReplyDto {
    private Long id;
    private String reply;

    public ReplyDto(Comment reply) {
        this.id = reply.getId();
        this.reply =reply.getComment();
    }

}
