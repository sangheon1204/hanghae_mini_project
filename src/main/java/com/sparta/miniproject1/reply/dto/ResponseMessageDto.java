package com.sparta.miniproject1.reply.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessageDto {
    String message;

    public ResponseMessageDto(String message) {
        this.message = message;
    }
}
