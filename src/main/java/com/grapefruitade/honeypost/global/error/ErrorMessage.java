package com.grapefruitade.honeypost.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class ErrorMessage {

    private final int status;
    private final String message;

}
