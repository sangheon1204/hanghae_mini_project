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
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Boolean isReply;    //댓글인지 대댓글인지 판별

    @Column
    private Long referenceId;   //참조한 댓글의 id

    @Column(nullable = false)
    private boolean state = true;

    public Comment(Long postId, Long userId, String comment, Boolean isReply, Long referenceId) {
        this.postId = postId;
        this.userId = userId;
        this.comment = comment;
        this.isReply = isReply;
        this.referenceId = referenceId;
    }

    public void update(String comment) {
        this.comment = comment;
    }

    public void deleteComment() {
        this.state = false;
    }
}
