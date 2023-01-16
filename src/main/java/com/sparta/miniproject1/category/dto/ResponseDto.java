package com.sparta.miniproject1.category.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    String message;

    public ResponseDto(String message) {
        this.message = message;
    }
}
