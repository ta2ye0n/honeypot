package com.grapefruitade.honeypost.domain.post.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;

public class PostNotFoundException extends CustomException {

    public PostNotFoundException(){
        super(ErrorCode.POST_NOT_FOUND);
    }

}
