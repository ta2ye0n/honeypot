package com.grapefruitade.honeypost.domain.comment.presentation.dto.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DetailCommentRes {
    private String author;
    private String comment;
}
