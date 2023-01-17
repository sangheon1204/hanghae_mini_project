package com.sparta.miniproject1.user.dto;

import com.sparta.miniproject1.image.dto.ImageResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    private String username;
    private String nickname;
    private String password;
    private String passwordCheck;
    private ImageResponseDto imageResponseDto;

    private boolean admin = false;
    private String adminToken = "";
}