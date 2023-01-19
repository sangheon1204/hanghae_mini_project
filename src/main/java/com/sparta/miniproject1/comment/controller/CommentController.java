package com.sparta.miniproject1.comment.controller;

import com.sparta.miniproject1.comment.dto.CommentRequestDto;
import com.sparta.miniproject1.comment.dto.ResponseCommentDto;
import com.sparta.miniproject1.comment.dto.ResponseCommentListDto;
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
    public ResponseCommentDto create(@RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.create(request, userDetails.getUser());
    }

    @ApiOperation(value = "댓글 받기", notes = "댓글들을 게시글의 id를 받아서 ")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @GetMapping("/comments/{id}")
    public ResponseCommentListDto get(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.get(id, userDetails.getUser());
    }

    @ApiOperation(value = "댓글 수정", notes = "댓글을 댓글의 id로 찾아 수정한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PatchMapping("/comments/{id}")
    public ResponseMessageDto update(@PathVariable Long id,@RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.update(id, request, userDetails.getUser());
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글을 댓글의 id로 찾아 삭제한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @DeleteMapping("/comments/{id}")
    public ResponseMessageDto delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.softDelete(id, userDetails.getUser());
    }
}
