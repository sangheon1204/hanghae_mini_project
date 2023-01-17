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

    @Column
    private Boolean state;

    @Column
    private Long userId;

    @Column
    private Long postId;

    public Wish(User user, Post post) {
        this.userId = user.getId();
        this.postId = post.getId();
        this.state = true;
    }

    public void changeState() {
        if(this.state == true) {
            this.state = false;
        }else {
            this.state = true;
        }
    }
}
