package com.sparta.miniproject1.post.dto;

import com.sparta.miniproject1.reply.entity.Reply;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ReplyDto {
    private Long id;
    private String reply;

    public ReplyDto(Reply reply) {
        this.id = reply.getId();
        this.reply =reply.getReply();
    }

}
