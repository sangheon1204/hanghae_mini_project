package com.sparta.miniproject1.post.entity;

import com.sparta.miniproject1.comment.entity.Comment;
import com.sparta.miniproject1.post.dto.PostRequestDto;
import com.sparta.miniproject1.user.entity.User;
import com.sparta.miniproject1.wish.entity.Wish;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //상품명
    @Column(nullable = false)
    private String name;
    //카테고리
    @Column(nullable = false)
    private String category;
    //상품의 가격
    @Column(nullable = false)
    private String price;
    //상품 설명
    @Column
    private String description;
    //상품 이미지 url
    @Column
    private String imageUrl;

    //게시글을 작성한 유저
    @Column
    private Long userId;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Wish> wishList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Comment> commentList = new ArrayList<>();


    public Post(PostRequestDto postRequestDto, User user) {
        this.name = postRequestDto.getName();
        this.category = postRequestDto.getCategory();
        this.price = postRequestDto.getPrice();
        this.description = postRequestDto.getDescription();
        this.userId = user.getId();
        this.imageUrl = postRequestDto.getImageResponseDto().getUrl();
    }

    public void update(PostRequestDto request) {
        if(request.getName() != null) {
            this.name = request.getName();
        }
        if(request.getDescription() != null) {
            this.description = request.getDescription();
        }
        if(request.getPrice() != null) {
            this.price = request.getPrice();
        }
        if(request.getCategory() != null) {
            this.category = request.getCategory();
        }

    }
}
