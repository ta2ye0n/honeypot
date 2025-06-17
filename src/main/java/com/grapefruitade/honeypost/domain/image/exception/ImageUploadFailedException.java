package com.grapefruitade.honeypost.domain.image.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class ImageUploadFailedException extends CustomException {
    public ImageUploadFailedException() {
        super(ErrorCode.IMAGE_UPLOAD_FAILED);
    }
}
