package com.grapefruitade.honeypost.domain.post.dto;

import com.grapefruitade.honeypost.domain.comment.dto.DetailComment;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostDetails {
    private Long postId;
    private String author;
    private String title;
    private String content;
    private Integer likes;
    private List<String> images;
    private List<DetailComment> comments;
}
