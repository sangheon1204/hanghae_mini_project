package com.sparta.miniproject1.reply.controller;

import com.sparta.miniproject1.reply.dto.ReplyRequestDto;
import com.sparta.miniproject1.reply.dto.ResponseMessageDto;
import com.sparta.miniproject1.reply.service.ReplyService;
import com.sparta.miniproject1.user.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Reply"})
@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @ApiOperation(value = "대댓글 추가", notes = "대댓글을 댓글의 id를 받아서 추가한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PostMapping("/replies")    //id 는 comment 의 아이디
    public ResponseMessageDto create(@RequestBody ReplyRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.create(request, userDetails.getUser());
    }

    @ApiOperation(value = "대댓글 수정", notes = "대댓글을 대댓글의 id로 찾아 수정한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PatchMapping("/replies")   //id 는 reply 의 아이디
    public ResponseMessageDto update(@RequestBody ReplyRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.update(request, userDetails.getUser());
    }

    @ApiOperation(value = "대댓글 삭제", notes = "대댓글을 대댓글의 id로 찾아 삭제한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @DeleteMapping("/replies")
    public ResponseMessageDto delete(@RequestBody ReplyRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.delete(request, userDetails.getUser());
    }
}
