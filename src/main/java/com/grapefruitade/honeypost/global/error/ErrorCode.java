package com.grapefruitade.honeypost.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // TOKEN
    TOKEN_NOT_VALID(401,"TOKEN_NOT_VALID"),
    TOKEN_IS_EXPIRATION(401,"TOKEN_IS_EXPIRATION"),
    TOKEN_NOT_FOUND(404, "TOKEN_NOT_FOUND"),


    // USER
    USER_NOT_FOUND(404, "USER_NOT_FOUND"),
    MISMATCH_USER_PASSWORD(400, "MISMATCH_USER_PASSWORD"),
    ALREADY_EXIST_USERNAME(409, "ALREADY_EXIST_USERNAME");


    private final int status;
    private final String message;

}
