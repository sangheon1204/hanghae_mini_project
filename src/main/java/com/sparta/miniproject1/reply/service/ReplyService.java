package com.sparta.miniproject1.reply.service;

import com.sparta.miniproject1.comment.entity.Comment;
import com.sparta.miniproject1.comment.repository.CommentRepository;
import com.sparta.miniproject1.reply.dto.ReplyRequestDto;
import com.sparta.miniproject1.reply.dto.ResponseMessageDto;
import com.sparta.miniproject1.reply.entity.Reply;
import com.sparta.miniproject1.reply.repository.ReplyRepository;
import com.sparta.miniproject1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseMessageDto create(ReplyRequestDto request, User user, HttpServletResponse response) {
        if(commentRepository.findById(request.getId()).isEmpty()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new IllegalArgumentException("해당 아이디의 댓글이 존재하지 않습니다.");
        }
        Comment comment = commentRepository.findById(request.getId()).get();

        Reply reply = new Reply(comment, user.getUsername(), request.getComment());

        replyRepository.save(reply);
        return new ResponseMessageDto("저장 성공!");
    }

    @Transactional
    public ResponseMessageDto update(ReplyRequestDto request, User user, HttpServletResponse response) {
        if(replyRepository.findById(request.getId()).isEmpty()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new IllegalArgumentException("해당 아이디의 대댓글이 존재하지 않습니다.");
        }
        Reply reply = replyRepository.findById(request.getId()).get();

        if(!reply.getUsername().equals(user.getUsername())) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new IllegalArgumentException("본인이 작성한 대댓글만 수정 가능합니다!");
        }

        reply.update(request.getComment());
        return new ResponseMessageDto("수정 성공!");
    }

    @Transactional
    public ResponseMessageDto delete(ReplyRequestDto request, User user, HttpServletResponse response) {
        if(replyRepository.findById(request.getId()).isEmpty()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new IllegalArgumentException("해당 아이디의 대댓글이 존재하지 않습니다.");
        }
        Reply reply = replyRepository.findById(request.getId()).get();

        if (!reply.getUsername().equals(user.getUsername())) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new IllegalArgumentException("본인이 작성한 댓글만 삭제 가능합니다!");
        }

        replyRepository.deleteById(request.getId());
        return new ResponseMessageDto("삭제 성공!");
    }
}
