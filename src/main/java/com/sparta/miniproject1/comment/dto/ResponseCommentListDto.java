package com.sparta.miniproject1.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseCommentListDto {
    private List<CommentList> comments;
    public ResponseCommentListDto(List<CommentList> comments) {
        this.comments = comments;
    }
}
