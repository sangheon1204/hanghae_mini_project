package com.sparta.miniproject1.product.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

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
    //상품의 이미지 url
    @Column(nullable = false)
    private String imageUrl;
    //상품의 가격
    @Column(nullable = false)
    private int price;

    //게시글을 작성한 유저
    @ManyToOne
    @JoinColumn
    private User user;

    //게시글에 달려있는 댓글 리스트
    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

}
