package com.grapefruitade.honeypost.domain.post.exception;

public class MaximumImagesExceededException extends RuntimeException {
  public MaximumImagesExceededException(String message) {
    super(message);
  }
}
