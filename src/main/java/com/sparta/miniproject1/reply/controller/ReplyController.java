package com.sparta.miniproject1.reply.controller;

import com.sparta.miniproject1.reply.dto.ReplyRequestDto;
import com.sparta.miniproject1.reply.dto.ResponseMessageDto;
import com.sparta.miniproject1.reply.service.ReplyService;
import com.sparta.miniproject1.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/replies")    //id 는 comment 의 아이디
    public ResponseMessageDto create(@RequestBody ReplyRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.create(request, userDetails.getUser());
    }

    @PatchMapping("/replies")   //id 는 reply 의 아이디
    public ResponseMessageDto update(@RequestBody ReplyRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.update(request, userDetails.getUser());
    }

    @DeleteMapping("/replies")
    public ResponseMessageDto delete(@RequestBody ReplyRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.delete(request, userDetails.getUser());
    }
}
