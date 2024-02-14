package com.grapefruitade.honeypost.domain.auth.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;

public class ExistUsernameException extends CustomException {

    public ExistUsernameException(){
        super(ErrorCode.ALREADY_EXIST_USERNAME);
    }
}
