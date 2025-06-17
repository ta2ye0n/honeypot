package com.grapefruitade.honeypost.domain.post.dto.res;

import com.grapefruitade.honeypost.domain.comment.presentation.dto.res.DetailCommentRes;
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
    private List<DetailCommentRes> comments;
}
