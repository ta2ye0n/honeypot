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

    // 이미지 엔티티 리스트를 받아 이미지 경로 리스트로 변환하는 메서드
    public static List<String> extractImagePaths(List<Image> images) {
        return images.stream()
                .map(Image::getPath)
                .collect(Collectors.toList());
    }
}
