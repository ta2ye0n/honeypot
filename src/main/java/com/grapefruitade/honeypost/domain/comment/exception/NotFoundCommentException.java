package com.grapefruitade.honeypost.domain.comment.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundCommentException extends CustomException {

    public NotFoundCommentException(){
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
