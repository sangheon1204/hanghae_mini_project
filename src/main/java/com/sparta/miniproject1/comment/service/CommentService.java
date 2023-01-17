package com.sparta.miniproject1.comment.service;

import com.sparta.miniproject1.comment.dto.CommentRequestDto;
import com.sparta.miniproject1.comment.dto.ResponseMessageDto;
import com.sparta.miniproject1.comment.entity.Comment;
import com.sparta.miniproject1.comment.repository.CommentRepository;
import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.post.repository.PostRepository;
import com.sparta.miniproject1.reply.entity.Reply;
import com.sparta.miniproject1.reply.repository.ReplyRepository;
import com.sparta.miniproject1.user.entity.User;
import com.sparta.miniproject1.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public ResponseMessageDto create(CommentRequestDto request, User user) {
        Post post = postRepository.findById(request.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 게시글이 존재하지 않습니다.")
        );

        Comment comment = new Comment(post, user.getId(), request.getComment());

        commentRepository.save(comment);
        return new ResponseMessageDto("저장 성공!");
    }

    @Transactional
    public ResponseMessageDto update(CommentRequestDto request, User user) {
        Comment comment = commentRepository.findById(request.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 댓글이 존재하지 않습니다.")
        );

        if(!comment.getUserId().equals(user.getId())) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 수정 가능합니다!");
        }

        comment.update(request.getComment());
        return new ResponseMessageDto("수정 성공!");
    }

    @Transactional
    public ResponseMessageDto delete(CommentRequestDto request, User user) {
        Comment comment = commentRepository.findById(request.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 댓글이 존재하지 않습니다.")
        );

        if(!(comment.getUserId().equals(user.getId()) || user.getRole().equals(UserRoleEnum.ADMIN))) {    //admin 이면 삭제 가능
            throw new IllegalArgumentException("본인이 작성한 댓글만 삭제 가능합니다!");
        }
        List<Reply> replyList = replyRepository.findALlByCommentId(comment.getId());
        //대댓글 삭제
        replyRepository.deleteAll(replyList);
        commentRepository.deleteById(request.getId());
        return new ResponseMessageDto("삭제 성공!");
    }
}
