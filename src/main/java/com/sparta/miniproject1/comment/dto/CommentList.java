package com.sparta.miniproject1.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentList {
    private Long id;
    private boolean state;
    private String comment;
    private List<ReplyList> replies;

    public CommentList(Long id, boolean state, String comment, List<ReplyList> replies) {
        this.id = id;
        this.state = state;
        this.comment = comment;
        this.replies = replies;
    }
}
