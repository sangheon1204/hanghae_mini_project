package com.sparta.miniproject1.comment.service;

import com.sparta.miniproject1.comment.dto.CommentRequestDto;
import com.sparta.miniproject1.comment.dto.CommentResponseDto;
import com.sparta.miniproject1.comment.entity.Comment;
import com.sparta.miniproject1.comment.repository.CommentRepository;
import com.sparta.miniproject1.product.entity.Post;
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
    public CommentResponseDto create(CommentRequestDto request, HttpServletResponse response) {
        if(postRepository.findById(request.getPostId()).isEmpty()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new IllegalArgumentException("해당 아이디의 게시물이 존재하지 않습니다.");
        }
        Post post = postRepository.findById(request.getPostId()).get();

        Comment comment = new Comment(post, request);

        commentRepository.save(comment);
        return new CommentResponseDto();
    }
}
