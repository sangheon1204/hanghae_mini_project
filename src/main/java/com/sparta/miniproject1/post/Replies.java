package com.sparta.miniproject1.post;

import com.sparta.miniproject1.comment.entity.Comment;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class Replies {
    private List<Comment> replies = new ArrayList<>();
    //생성자
    public Replies(List<Comment> replies) {
        this.replies = replies;

    }
    public List<Comment> getReplies() {
        return replies;
    }
}
