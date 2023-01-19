package com.sparta.miniproject1.comment.dto;

import com.sparta.miniproject1.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyList {
    private Long id;
    private boolean state;
    private String reply;

    public ReplyList(Long id, boolean state, Comment reply) {
        this.id = id;
        this.state = state;
        this.reply = reply.getComment();
    }
}
