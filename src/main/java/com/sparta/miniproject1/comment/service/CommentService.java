package com.sparta.miniproject1.comment.service;

import com.sparta.miniproject1.comment.dto.CommentRequestDto;
import com.sparta.miniproject1.comment.dto.ResponseMessageDto;
import com.sparta.miniproject1.comment.entity.Comment;
import com.sparta.miniproject1.comment.repository.CommentRepository;
import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.post.repository.PostRepository;
import com.sparta.miniproject1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public ResponseMessageDto create(CommentRequestDto request, User user, HttpServletResponse response) {
        if(postRepository.findById(request.getId()).isEmpty()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new IllegalArgumentException("해당 아이디의 게시글이 존재하지 않습니다.");
        }
        Post post = postRepository.findById(request.getId()).get();

        Comment comment = new Comment(post, user, request.getComment());

        commentRepository.save(comment);
        return new ResponseMessageDto("저장 성공!");
    }

    @Transactional
    public ResponseMessageDto update(CommentRequestDto request, User user, HttpServletResponse response) {
        if(commentRepository.findById(request.getId()).isEmpty()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new IllegalArgumentException("해당 아이디의 댓글이 존재하지 않습니다.");
        }
        Comment comment = commentRepository.findById(request.getId()).get();

        if(!comment.getUser().getUsername().equals(user.getUsername())) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new IllegalArgumentException("본인이 작성한 댓글만 수정 가능합니다!");
        }

        comment.update(request.getComment());
        return new ResponseMessageDto("수정 성공!");
    }

    @Transactional
    public ResponseMessageDto delete(CommentRequestDto request, User user, HttpServletResponse response) {
        if(commentRepository.findById(request.getId()).isEmpty()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new IllegalArgumentException("해당 아이디의 댓글이 존재하지 않습니다.");
        }
        Comment comment = commentRepository.findById(request.getId()).get();

        if(!comment.getUser().getUsername().equals(user.getUsername())) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new IllegalArgumentException("본인이 작성한 댓글만 삭제 가능합니다!");
        }

        commentRepository.deleteById(request.getId());
        return new ResponseMessageDto("삭제 성공!");
    }
}
