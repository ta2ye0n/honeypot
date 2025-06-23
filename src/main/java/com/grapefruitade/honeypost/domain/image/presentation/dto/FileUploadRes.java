package com.grapefruitade.honeypost.domain.image.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FileUploadRes {
    String imageUrl;
}
