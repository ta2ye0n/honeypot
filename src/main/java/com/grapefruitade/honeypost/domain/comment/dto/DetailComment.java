package com.grapefruitade.honeypost.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DetailComment {
    private String author;
    private String comment;
}
