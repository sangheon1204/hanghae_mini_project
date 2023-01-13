package com.sparta.miniproject1.comment.controller;

import com.sparta.miniproject1.comment.dto.CommentRequestDto;
import com.sparta.miniproject1.comment.dto.ResponseMessageDto;
import com.sparta.miniproject1.comment.service.CommentService;
import com.sparta.miniproject1.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comments")   //request 에 post 의 아이디가 들어있음
    public ResponseMessageDto create(@RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        return commentService.create(request, userDetails.getUser(), response);
    }

    @PutMapping("/comments")    //request 에 comment 의 아이디가 들어있음
    public ResponseMessageDto update(@RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        return commentService.update(request, userDetails.getUser(), response);
    }

    @DeleteMapping("/comments")
    public ResponseMessageDto delete(@RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        return commentService.delete(request, userDetails.getUser(), response);
    }
}
