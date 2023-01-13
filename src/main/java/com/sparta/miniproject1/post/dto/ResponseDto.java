package com.sparta.miniproject1.post.dto;

import lombok.Getter;

@Getter
public class ResponseDto {
    private String message;

    public ResponseDto(String msg) {
        this.message = msg;
    }
}