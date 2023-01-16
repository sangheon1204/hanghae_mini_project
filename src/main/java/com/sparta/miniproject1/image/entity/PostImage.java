package com.sparta.miniproject1.image.entity;

import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //파일 url
    @Column
    private String imageUrl;
    //file 올린 사람
    @ManyToOne
    @JoinColumn
    private User user;
    //상품 사진이 올려진 해당 게시글
    @ManyToOne
    @JoinColumn
    private Post post;

    public PostImage(String imageUrl, User user, Post post) {
        this.imageUrl = imageUrl;
        this.user = user;
        this.post = post;
    }
}
