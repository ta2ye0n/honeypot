package com.grapefruitade.honeypost.domain.post.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class UserNotSameException extends CustomException {

    public UserNotSameException(){
        super(ErrorCode.USER_NOT_SAME);
    }
}
