package com.grapefruitade.honeypost.domain.image.exception;

public class ImageUploadFailedException extends RuntimeException {
  public ImageUploadFailedException(String message) {
    super(message);
  }
}
