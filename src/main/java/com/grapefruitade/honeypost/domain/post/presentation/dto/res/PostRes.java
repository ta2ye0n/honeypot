package com.grapefruitade.honeypost.domain.post.presentation.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostRes {
    private Long postId;
    private String author;
    private String title;
    private String content;
    private Integer likes;
    private Integer comments;
    private String previewImage;
    private Boolean likeStatus;
}
