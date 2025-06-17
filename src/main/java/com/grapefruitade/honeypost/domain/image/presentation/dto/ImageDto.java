package com.grapefruitade.honeypost.domain.image.dto;

import com.grapefruitade.honeypost.domain.image.entity.Image;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ImageDto {
    private String originalName;
    private String saveName;
    private String url;
    private Long postId;

    public Image toEntity(Post post) {
        return Image.builder()
                .url(url)
                .originalName(originalName)
                .saveName(saveName)
                .post(post)
                .build();
    }

    @Builder
    public ImageDto(String original_name, String save_name, String path, Long postId) {
        this.originalName = original_name;
        this.saveName = save_name;
        this.url = path;
        this.postId = postId;
    }

}
