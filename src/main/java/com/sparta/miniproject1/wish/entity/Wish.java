package com.sparta.miniproject1.wish.entity;

import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    Post post;

    @Column
    private boolean state = true;

    @Column
    private Long userId;


    public Wish(User user, Post post) {
        this.userId = user.getId();
        this.post = post;
    }

    public void updateWish() {
        if(this.state) {
            this.state = false;
            return;
        }
        this.state = true;
    }
}
