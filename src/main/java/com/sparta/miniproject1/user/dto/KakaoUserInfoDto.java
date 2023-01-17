package com.sparta.miniproject1.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String nicknmae;

    public KakaoUserInfoDto(Long id, String nicknmae) {
        this.id = id;
        this.nicknmae = nicknmae;
    }
}