package com.sparta.miniproject1.comment.entity;

import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.reply.entity.Reply;
import com.sparta.miniproject1.timestamped.Timestamped;
import com.sparta.miniproject1.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long id;

    @Column
    private Long postId;

    @Column
    private Long userId;

    @Column(nullable = false)
    String comment;

    public Comment(Post post, Long userId, String comment) {
        this.postId = post.getId();
        this.userId = userId;
        this.comment = comment;
    }

    public void update(String comment) {
        this.comment = comment;
    }
}
