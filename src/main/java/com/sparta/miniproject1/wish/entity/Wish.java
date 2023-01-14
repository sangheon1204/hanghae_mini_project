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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Wish(User user, Post post) {
        this.user = user;
        this.post = post;
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
