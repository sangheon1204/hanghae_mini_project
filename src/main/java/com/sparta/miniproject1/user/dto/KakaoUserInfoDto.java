package com.sparta.miniproject1.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String username;
    private String nickname;

    public KakaoUserInfoDto(Long id, String username, String nickname) {
        this.id = id;
        this.username= username;
        this.nickname = nickname;
    }
}