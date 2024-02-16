package com.grapefruitade.honeypost.domain.comment.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;

public class InvalidPost extends CustomException {

    public InvalidPost(){
        super(ErrorCode.INVALID_POST);
    }

}
