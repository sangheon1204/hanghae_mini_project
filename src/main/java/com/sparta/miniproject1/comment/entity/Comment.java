package com.sparta.miniproject1.comment.entity;

import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.reply.entity.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Comment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Post post;

    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    String comment;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Reply> replyList = new ArrayList<>();

    public Comment(Post post, String username, String comment) {
        this.post = post;
        this.username = username;
        this.comment = comment;
    }

    public void update(String comment) {
        this.comment = comment;
    }
}
