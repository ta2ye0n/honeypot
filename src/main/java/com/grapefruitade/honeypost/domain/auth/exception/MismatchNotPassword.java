package com.grapefruitade.honeypost.domain.auth.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;

public class MismatchNotPassword extends CustomException {

    public MismatchNotPassword(){
        super(ErrorCode.MISMATCH_USER_PASSWORD);
    }
}
