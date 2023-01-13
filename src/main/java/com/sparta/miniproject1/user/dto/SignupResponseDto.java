package com.sparta.miniproject1.user.dto;

import com.sparta.miniproject1.user.entity.User;
import com.sparta.miniproject1.user.service.UserService;
import lombok.Getter;

@Getter
public class SignupResponseDto {

    private String username;
    private String role;

    public SignupResponseDto(User user){
    this.username=user.getUsername();
    this.role= String.valueOf(user.getRole());
    }
}