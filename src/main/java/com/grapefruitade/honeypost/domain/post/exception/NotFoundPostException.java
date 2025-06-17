package com.grapefruitade.honeypost.domain.post.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundPostException extends CustomException {

    public NotFoundPostException(){
        super(ErrorCode.POST_NOT_FOUND);
    }

}
