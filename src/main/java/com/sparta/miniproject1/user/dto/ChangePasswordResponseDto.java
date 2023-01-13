package com.sparta.miniproject1.user.dto;

import com.sparta.miniproject1.user.entity.User;

public class ChangePasswordResponseDto {

    private String password;

    public ChangePasswordResponseDto(User user){
        this.password = user.getPassword();
    }
}
