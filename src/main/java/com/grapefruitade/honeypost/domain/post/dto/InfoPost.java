package com.grapefruitade.honeypost.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InfoPost {
    private Long postId;
    private String author;
    private String title;
    private String content;
    private Integer likes;
    private String previewImage;
    private Integer comments;

}
