package com.grapefruitade.honeypost.domain.comment.presentation.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommentListRes {
    List<CommentRes> comments;
}
