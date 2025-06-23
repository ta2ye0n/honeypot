package com.grapefruitade.honeypost.domain.comment.presentation.dto.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentRes {
    private Long id;
    private String author;
    private String comment;
}
