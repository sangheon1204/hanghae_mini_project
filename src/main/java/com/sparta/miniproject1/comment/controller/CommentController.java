package com.sparta.miniproject1.comment.controller;

import com.sparta.miniproject1.comment.dto.CommentRequestDto;
import com.sparta.miniproject1.comment.dto.ResponseMessageDto;
import com.sparta.miniproject1.comment.service.CommentService;
import com.sparta.miniproject1.user.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Comment"})
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @ApiOperation(value = "댓글 추가", notes = "댓글을 게시글의 id를 받아서 추가한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PostMapping("/comments")   //request 에 post 의 아이디가 들어있음
    public ResponseMessageDto create(@RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.create(request, userDetails.getUser());
    }

    @ApiOperation(value = "댓글 수정", notes = "댓글을 댓글의 id로 찾아 수정한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PatchMapping("/comments")    //request 에 comment 의 아이디가 들어있음
    public ResponseMessageDto update(@RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.update(request, userDetails.getUser());
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글을 댓글의 id로 찾아 삭제한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @DeleteMapping("/comments")
    public ResponseMessageDto delete(@RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.delete(request, userDetails.getUser());
    }
}
