package com.grapefruitade.honeypost.domain.comment.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;

public class CommentNotFound extends CustomException {

    public CommentNotFound(){
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
