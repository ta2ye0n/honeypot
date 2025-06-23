package com.grapefruitade.honeypost.domain.image.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class NotAllowedFileException extends CustomException {
    public NotAllowedFileException() {
        super(ErrorCode.NOT_ALLOWED_FILE);
    }
}
