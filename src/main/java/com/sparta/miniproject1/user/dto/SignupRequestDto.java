package com.sparta.miniproject1.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {

    private String username;
    private String password;
    private String passwordCheck;

    private boolean admin = false;
    private String adminToken = "";
}