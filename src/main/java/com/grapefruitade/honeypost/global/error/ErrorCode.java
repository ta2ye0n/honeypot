package com.grapefruitade.honeypost.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    TOKEN_NOT_VALID(401,"TOKEN_NOT_VALID"),
    TOKEN_IS_EXPIRATION(401,"TOKEN_IS_EXPIRATION"),
    USER_NOT_FOUND(404, "USER_NOT_FOUND"),
    ALREADY_EXIST_USERNAME(409, "ALREADY_EXIST_USERNAME");

    private final int status;
    private final String message;

}
