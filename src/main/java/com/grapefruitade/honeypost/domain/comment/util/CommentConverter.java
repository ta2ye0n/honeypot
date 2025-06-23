package com.grapefruitade.honeypost.domain.comment.util;

import com.grapefruitade.honeypost.domain.comment.entity.Comment;
import com.grapefruitade.honeypost.domain.comment.presentation.dto.res.CommentRes;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    public CommentRes toDto(Comment comment) {
        return CommentRes.builder()
                .id(comment.getId())
                .author(comment.getAuthor().getNickname())
                .comment(comment.getContent())
                .build();
    }
}
