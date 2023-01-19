package com.sparta.miniproject1.comment.dto;

import com.sparta.miniproject1.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCommentDto {
    Long id;
    String comment;

    public ResponseCommentDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
    }
}
