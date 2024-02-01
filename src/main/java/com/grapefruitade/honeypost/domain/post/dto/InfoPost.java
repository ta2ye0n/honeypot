package com.grapefruitade.honeypost.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class InfoPost {
    private Long postId;
    private String author;
    private String title;
    private String content;
    private Integer likes;

}
