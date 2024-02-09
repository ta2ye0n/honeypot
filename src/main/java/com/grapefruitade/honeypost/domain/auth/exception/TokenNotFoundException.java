package com.grapefruitade.honeypost.domain.auth.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;

public class TokenNotFoundException extends CustomException {

    public TokenNotFoundException(){
        super(ErrorCode.TOKEN_NOT_FOUND);
    }



}
