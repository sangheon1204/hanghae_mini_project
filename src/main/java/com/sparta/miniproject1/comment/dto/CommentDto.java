package com.sparta.miniproject1.comment.dto;

import com.sparta.miniproject1.post.dto.ReplyDto;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentDto {
    private Long id;
    private String comments;
    private List<ReplyDto> replyDtoList;
    private boolean commentState;

    public CommentDto(Long id, String comments, List<ReplyDto> replyDtoList, boolean commentState) {
        this.id = id;
        this.comments = comments;
        this.replyDtoList = replyDtoList;
        this.commentState = commentState;
    }

}
