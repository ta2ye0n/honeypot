package com.grapefruitade.honeypost.domain.comment.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;

public class UserNotSame extends CustomException {

    public UserNotSame(){
        super(ErrorCode.USER_NOT_SAME);
    }
}
