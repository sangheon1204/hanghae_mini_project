package com.sparta.miniproject1.reply.entity;

import com.sparta.miniproject1.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    Comment comment;

    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    String reply;

    public Reply(Comment comment, String username, String reply) {
        this.comment = comment;
        this.username = username;
        this.reply = reply;
    }

    public void update(String reply) {
        this.reply = reply;
    }
}
