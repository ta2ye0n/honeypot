package com.grapefruitade.honeypost.domain.post.dto;

import com.grapefruitade.honeypost.domain.image.entity.Image;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class PostInfo {
    private Long postId;
    private String author;
    private String title;
    private String content;
    private Integer likes;
    private List<String> images;
}
