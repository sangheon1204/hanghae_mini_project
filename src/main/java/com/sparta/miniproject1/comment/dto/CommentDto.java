package com.sparta.miniproject1.comment.dto;

import com.sparta.miniproject1.comment.entity.Comment;
import com.sparta.miniproject1.post.Replies;
import com.sparta.miniproject1.post.dto.ReplyDto;
import com.sparta.miniproject1.reply.entity.Reply;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentDto {
    private Long id;
    private String comments;
    private List<ReplyDto> replyDtoList;

    public CommentDto(Long id, String comments, List<ReplyDto> replyDtoList) {
        this.id = id;
        this.comments = comments;
        this.replyDtoList = replyDtoList;
    }

}
