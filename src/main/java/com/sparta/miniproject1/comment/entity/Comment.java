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

    @ManyToOne
    @JoinColumn
    private Post post;

    @Column
    private Long userId;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Reply> replyList = new ArrayList<>();

    @Column(nullable = false)
    String comment;

    public Comment(Post post, Long userId, String comment) {
        this.post = post;
        this.userId = userId;
        this.comment = comment;
    }

    public void update(String comment) {
        this.comment = comment;
    }
}
