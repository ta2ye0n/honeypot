package com.grapefruitade.honeypost.domain.comment.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;

public class CommentNotFoundException extends CustomException {

    public CommentNotFoundException(){
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
