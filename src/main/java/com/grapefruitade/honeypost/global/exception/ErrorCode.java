package com.grapefruitade.honeypost.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    MAXIMUM_IMAGES_EXCEEDED (HttpStatus.BAD_REQUEST, "이미지는 최대 7장까지 가능합니다."),
    INVALID_EXTENSION(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "잘못된 이미지 확장자 입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
