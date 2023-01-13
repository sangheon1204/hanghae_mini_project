package com.sparta.miniproject1.post;

import com.sparta.miniproject1.comment.entity.Comment;

import java.util.ArrayList;
import java.util.List;

public class Comments {
    private List<Comment> comments = new ArrayList<>();
    //생성자
    public Comments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
