package com.sparta.miniproject1.category.entity;

import com.sparta.miniproject1.category.dto.RequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Category {     //상품 등록 페이지 들어갈 때 줘야됨
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String category;

    public Category(RequestDto request) {
        this.category = request.getCategory();
    }
}
