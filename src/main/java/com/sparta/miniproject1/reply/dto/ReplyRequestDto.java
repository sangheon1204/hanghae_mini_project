package com.sparta.miniproject1.reply.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyRequestDto {
    Long id;    //comment or reply
    String comment;
}
