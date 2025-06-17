package com.grapefruitade.honeypost.domain.image.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class MaximumImagesExceededException extends CustomException {
    public MaximumImagesExceededException() {
        super(ErrorCode.MAXIMUM_IMAGES_EXCEEDED);
    }
}
