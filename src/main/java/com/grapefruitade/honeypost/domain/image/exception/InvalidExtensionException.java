package com.grapefruitade.honeypost.domain.image.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidExtensionException extends CustomException {
    public InvalidExtensionException() {
        super(ErrorCode.INVALID_EXTENSION);
    }
}
