package com.sparta.miniproject1.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestApiException {
    private String errorMessage;
    private int httpStatus;
}