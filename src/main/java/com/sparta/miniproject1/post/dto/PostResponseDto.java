package com.sparta.miniproject1.post.dto;

import com.sparta.miniproject1.comment.dto.CommentDto;
import com.sparta.miniproject1.post.Comments;
import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long id;
    private String name;
    private String nickname;
    private String userUrl;
    private String description;
    private String price;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String imageUrl;
    private List<CommentDto> comments;
    private boolean state;

    public PostResponseDto(Post post, List<CommentDto> comments, User user, boolean state){
        this.id = post.getId();
        this.name = post.getName();
        this.nickname = user.getNickname();
        this.userUrl = user.getImgurl();
        this.description = post.getDescription();
        this.price = post.getPrice();
        this.category = post.getCategory();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.imageUrl = post.getImageUrl();
        this.comments = comments;
        this.state = state;
    }
}
