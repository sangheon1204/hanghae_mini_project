package com.sparta.miniproject1.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String username;
    private String nickname;
    private String imgurl;

    public KakaoUserInfoDto(Long id, String username, String nickname, String imgurl) {
        this.id = id;
        this.username= username;
        this.nickname = nickname;
        this.imgurl = imgurl;
    }
}