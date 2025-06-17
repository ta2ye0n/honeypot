package com.grapefruitade.honeypost.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // POST
    MAXIMUM_IMAGES_EXCEEDED(HttpStatus.BAD_REQUEST, "이미지는 최대 7장까지 가능합니다."),
    INVALID_EXTENSION(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "잘못된 이미지 확장자 입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_NOT_FOUND"),
    IMAGE_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "이미지 업로드를 실패했습니다."),

    // TOKEN
    TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED,"TOKEN_NOT_VALID"),
    TOKEN_IS_EXPIRATION(HttpStatus.UNAUTHORIZED,"TOKEN_IS_EXPIRATION"),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "TOKEN_NOT_FOUND"),

    // USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"),
    MISMATCH_USER_PASSWORD(HttpStatus.BAD_REQUEST, "MISMATCH_USER_PASSWORD"),
    ALREADY_EXIST_USERNAME(HttpStatus.CONFLICT, "ALREADY_EXIST_USERNAME"),
    USER_NOT_SAME(HttpStatus.UNAUTHORIZED, "USER_NOT_SAME"),


    // COMMENT
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_NOT_FOUND");

    private final HttpStatus httpStatus;
    private final String message;
}
