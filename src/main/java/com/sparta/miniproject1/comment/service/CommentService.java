package com.sparta.miniproject1.comment.service;

import com.sparta.miniproject1.comment.dto.CommentRequestDto;
import com.sparta.miniproject1.comment.dto.ResponseCommentDto;
import com.sparta.miniproject1.comment.dto.ResponseMessageDto;
import com.sparta.miniproject1.comment.entity.Comment;
import com.sparta.miniproject1.comment.repository.CommentRepository;
import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.post.repository.PostRepository;
import com.sparta.miniproject1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public ResponseCommentDto create(CommentRequestDto request, User user) {
        if(!request.getIsReply()) {    //댓글일 경우
            Post post = postRepository.findById(request.getReferenceId()).orElseThrow(
                    () -> new IllegalArgumentException("해당 아이디의 게시글이 존재하지 않습니다.")
            );
            Comment comment = new Comment(post.getId(), user.getId(), request.getComment(), request.getIsReply(), null);
            commentRepository.save(comment);
            return new ResponseCommentDto(comment);
        }
        else {     //대댓글일 경우
            Comment comment = commentRepository.findById(request.getReferenceId()).orElseThrow(
                    () -> new IllegalArgumentException("해당 아이디의 댓글이 존재하지 않습니다.")
            );
            Comment reply = new Comment(comment.getPostId(), user.getId(), request.getComment(), request.getIsReply(), comment.getId());
            commentRepository.save(reply);
            return new ResponseCommentDto(reply);
        }
    }

    @Transactional
    public ResponseMessageDto update(Long id, CommentRequestDto request, User user) {
        Comment comment = getComment(id, user);
        comment.update(request.getComment());
        return new ResponseMessageDto("수정 성공!");
    }

    @Transactional  //데이터 상태를 삭제로 변경
    public ResponseMessageDto softDelete(Long id, User user) {
        Comment comment = getComment(id, user);
        List<Comment> replyList = commentRepository.findAllByReferenceIdAndState(comment.getId(), true).orElse(new ArrayList<>());
        for(Comment reply: replyList) {
            reply.deleteComment();
        }
        comment.deleteComment();
        return new ResponseMessageDto("삭제 성공!");
    }

    private Comment getComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 댓글이 존재하지 않습니다.")
        );
        if(!comment.getUserId().equals(user.getId())) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 수정 가능합니다!");
        }
        return comment;
    }
}
