package com.sparta.miniproject1.post;

import com.sparta.miniproject1.reply.entity.Reply;

import java.util.ArrayList;
import java.util.List;

public class Replies {
    private List<Reply> replies = new ArrayList<>();
    //생성자
    public Replies(List<Reply> replies) {
        this.replies = replies;

    }
    public List<Reply> getReplies() {
        return replies;
    }
}
