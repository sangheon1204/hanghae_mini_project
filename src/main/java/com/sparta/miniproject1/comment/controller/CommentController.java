package com.sparta.miniproject1.comment.controller;

import com.sparta.miniproject1.comment.dto.CommentRequestDto;
import com.sparta.miniproject1.comment.dto.ResponseMessageDto;
import com.sparta.miniproject1.comment.service.CommentService;
import com.sparta.miniproject1.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comments")   //request 에 post 의 아이디가 들어있음
    public ResponseMessageDto create(@RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.create(request, userDetails.getUser());
    }

    @PatchMapping("/comments")    //request 에 comment 의 아이디가 들어있음
    public ResponseMessageDto update(@RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.update(request, userDetails.getUser());
    }

    @DeleteMapping("/comments")
    public ResponseMessageDto delete(@RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.delete(request, userDetails.getUser());
    }
}
