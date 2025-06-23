package com.grapefruitade.honeypost.domain.post.presentation.dto.res;

import com.grapefruitade.honeypost.domain.comment.presentation.dto.res.CommentListRes;
import com.grapefruitade.honeypost.domain.comment.presentation.dto.res.CommentRes;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostDetailsRes {
    private Long postId;
    private String author;
    private String title;
    private String content;
    private Integer likes;
    private CommentListRes comments;
    private Boolean likeStatus;
}
