package com.sparta.miniproject1.image.entity;

import com.sparta.miniproject1.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ProfileImage {
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

    public ProfileImage(String imageUrl, User user) {
        this.imageUrl = imageUrl;
        this.user = user;
    }
}
