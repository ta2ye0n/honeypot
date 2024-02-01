package com.grapefruitade.honeypost.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostInfo {
    private Long postId;
    private String author;
    private String title;
    private String content;
    private Integer likes;
}
