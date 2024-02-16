package com.grapefruitade.honeypost.domain.comment.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;

public class InvalidComment extends CustomException {

    public InvalidComment(){
        super(ErrorCode.INVALID_COMMENT);
    }
}
