package com.grapefruitade.honeypost.domain.post.util;

import com.grapefruitade.honeypost.domain.comment.presentation.dto.res.CommentListRes;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.PostDetailsRes;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.PostRes;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public PostRes toListDto(Post post) {
        String preview = post.getPreviewUrl() != null ? post.getPreviewUrl() : null;

        return PostRes.builder()
                .postId(post.getId())
                .author(post.getAuthor().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .likes(post.getLikes())
                .previewImage(preview)
                .build();
    }

    public PostDetailsRes toDto(Post post, CommentListRes commentListRes) {
        return PostDetailsRes.builder()
                .postId(post.getId())
                .author(post.getAuthor().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .likes(post.getLikes())
                .comments(commentListRes)
                .build();
    }
}
