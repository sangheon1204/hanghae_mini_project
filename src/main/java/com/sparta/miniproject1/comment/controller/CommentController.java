package com.sparta.miniproject1.comment.controller;

import com.sparta.miniproject1.comment.dto.CommentRequestDto;
import com.sparta.miniproject1.comment.dto.CommentResponseDto;
import com.sparta.miniproject1.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comments")
    public CommentResponseDto create(@RequestBody CommentRequestDto request, HttpServletResponse response) {
        commentService.create(request, response);
    }
}
