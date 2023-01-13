package com.sparta.miniproject1.user.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private String msg;

    public LoginResponseDto(String msg) {
        this.msg=msg;
    }
}