package com.sparta.miniproject1.reply.service;

import com.sparta.miniproject1.comment.entity.Comment;
import com.sparta.miniproject1.comment.repository.CommentRepository;
import com.sparta.miniproject1.reply.dto.ReplyRequestDto;
import com.sparta.miniproject1.reply.dto.ResponseMessageDto;
import com.sparta.miniproject1.reply.entity.Reply;
import com.sparta.miniproject1.reply.repository.ReplyRepository;
import com.sparta.miniproject1.user.entity.User;
import com.sparta.miniproject1.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseMessageDto create(ReplyRequestDto request, User user) {
        Comment comment = commentRepository.findById(request.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 댓글이 존재하지 않습니다.")
        );

        Reply reply = new Reply(comment, user, request.getComment());

        replyRepository.save(reply);
        return new ResponseMessageDto("저장 성공!");
    }

    @Transactional
    public ResponseMessageDto update(ReplyRequestDto request, User user) {
        Reply reply = replyRepository.findById(request.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 대댓글이 존재하지 않습니다.")
        );

        if(!reply.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("본인이 작성한 대댓글만 수정 가능합니다!");
        }

        reply.update(request.getComment());
        return new ResponseMessageDto("수정 성공!");
    }

    @Transactional
    public ResponseMessageDto delete(ReplyRequestDto request, User user) {
        Reply reply = replyRepository.findById(request.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 대댓글이 존재하지 않습니다.")
        );

        if (!(reply.getUser().getUsername().equals(user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN))) {     //admin 이면 삭제 가능
            throw new IllegalArgumentException("본인이 작성한 댓글만 삭제 가능합니다!");
        }

        replyRepository.deleteById(request.getId());
        return new ResponseMessageDto("삭제 성공!");
    }
}
