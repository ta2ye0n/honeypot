package com.grapefruitade.honeypost.domain.auth.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;

public class TokenExpirationException extends CustomException {

    public TokenExpirationException(){
        super(ErrorCode.TOKEN_IS_EXPIRATION);
    }
}
