package com.grapefruitade.honeypost.domain.comment.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;

public class PostNotFound extends CustomException {

    public PostNotFound(){
        super(ErrorCode.POST_NOT_FOUND);
    }

}
