package com.grapefruitade.honeypost.domain.post.presentation.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostListRes {
    List<PostRes> posts;
}
