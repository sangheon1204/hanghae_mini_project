package com.sparta.miniproject1.comment.entity;

import com.sparta.miniproject1.comment.dto.CommentRequestDto;
import com.sparta.miniproject1.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long id;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

//    @Column(nullable = false)
//    String username;

    @Column(nullable = false)
    String comment;

    public Comment(Post post, /*String username,*/ CommentRequestDto comment) {
        this.post = post;
//        this.username = username;
        this.comment = comment.getComment();
    }
}
