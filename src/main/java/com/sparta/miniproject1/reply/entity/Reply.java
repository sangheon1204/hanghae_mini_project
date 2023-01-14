package com.sparta.miniproject1.reply.entity;

import com.sparta.miniproject1.comment.entity.Comment;
import com.sparta.miniproject1.timestamped.Timestamped;
import com.sparta.miniproject1.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Reply extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    Comment comment;

    @ManyToOne
    @JoinColumn(nullable = false)
    User user;

    @Column(nullable = false)
    String reply;

    public Reply(Comment comment, User user, String reply) {
        this.comment = comment;
        this.user = user;
        this.reply = reply;
    }

    public void update(String reply) {
        this.reply = reply;
    }
}
