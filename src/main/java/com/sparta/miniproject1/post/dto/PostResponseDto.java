package com.sparta.miniproject1.post.dto;

import com.sparta.miniproject1.post.Comments;
import com.sparta.miniproject1.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private String name;
    private String description;
    private int price;
    private String category;
    private Comments comments;

    public PostResponseDto(Post post, Comments comments){
        this.name = post.getName();
        this.description = post.getDescription();
        this.price = post.getPrice();
        this.category = post.getCategory();
        this.comments = comments;
    }
}
