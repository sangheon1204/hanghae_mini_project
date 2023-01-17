package com.sparta.miniproject1.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    Long referenceId;    //상황에 따라 postId 혹은, commentId 로 사용
    String comment;
    Boolean isReply;
}
