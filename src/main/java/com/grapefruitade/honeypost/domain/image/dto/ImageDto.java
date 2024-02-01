package com.grapefruitade.honeypost.domain.image.dto;

import com.grapefruitade.honeypost.domain.image.entity.Image;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ImageDto {
    private String original_name;
    private String save_name;
    private String path;
    private Long post_id;

    public Image toEntity(Post post) {
        return Image.builder()
                .path(path)
                .original_name(original_name)
                .save_name(save_name)
                .post(post)
                .build();
    }

    @Builder
    public ImageDto(String original_name, String save_name, String path, Long post_id) {
        this.original_name = original_name;
        this.save_name = save_name;
        this.path = path;
        this.post_id = post_id;
    }

}
