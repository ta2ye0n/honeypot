package com.grapefruitade.honeypost.domain.auth.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException(){
        super(ErrorCode.USER_NOT_FOUND);
    }
}
