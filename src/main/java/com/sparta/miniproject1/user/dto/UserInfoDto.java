package com.sparta.miniproject1.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {

    private String username;
    private String nickname;
    private String imgurl;

    public UserInfoDto(String username, String nickname, String imgurl) {
        this.username= username;
        this.nickname = nickname;
        this.imgurl = imgurl;
    }
}