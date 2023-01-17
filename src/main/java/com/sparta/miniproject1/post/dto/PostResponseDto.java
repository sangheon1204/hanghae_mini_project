package com.sparta.miniproject1.post.dto;

import com.sparta.miniproject1.comment.dto.CommentDto;
import com.sparta.miniproject1.post.Comments;
import com.sparta.miniproject1.post.entity.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class PostResponseDto {
    private String name;
    private String description;
    private String price;
    private String category;
    private String imageUrl;
    private List<CommentDto> comments;

    public PostResponseDto(Post post, List<CommentDto> comments){
        this.name = post.getName();
        this.description = post.getDescription();
        this.price = post.getPrice();
        this.category = post.getCategory();
        this.imageUrl = post.getImageUrl();
        this.comments = comments;
    }
}
