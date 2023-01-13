package com.sparta.miniproject1.post.dto;

import com.sparta.miniproject1.post.entity.Post;
import lombok.Getter;

@Getter
public class PostRequestDto {
    private String name;
    private String description;
    private int price;
    private String category;
    private String image_url;

    //생성자
    public PostRequestDto(Post post) {
        this.name = post.getName();
        this.description = post.getDescription();
        this.price = post.getPrice();
        this.category = post.getCategory();
        this.image_url = post.getImageUrl();
    }
}
