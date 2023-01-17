package com.sparta.miniproject1.comment.entity;

import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.timestamped.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Post post;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    String comment;

    @Column(nullable = false)
    Boolean isReply;    //댓글인지 대댓글인지 판별

    @Column
    Long referenceId;   //참조한 댓글의 id

    public Comment(Post post, Long userId, String comment, Boolean isReply, Long referenceId) {
        this.post = post;
        this.userId = userId;
        this.comment = comment;
        this.isReply = isReply;
        this.referenceId = referenceId;
    }

    public void update(String comment) {
        this.comment = comment;
    }
}
