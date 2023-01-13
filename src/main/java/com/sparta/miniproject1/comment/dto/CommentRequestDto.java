package com.sparta.miniproject1.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    Long postId;
    String comment;
}
