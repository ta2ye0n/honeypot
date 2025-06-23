package com.grapefruitade.honeypost.domain.post.service;

import com.grapefruitade.honeypost.domain.like.repository.LikeRepository;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.PostListRes;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.PostRes;
import com.grapefruitade.honeypost.domain.post.util.PostConverter;
import com.grapefruitade.honeypost.global.annotation.ServiceWithReadOnlyTransaction;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ServiceWithReadOnlyTransaction
@RequiredArgsConstructor
public class GetHopTopicPostService {
    private final LikeRepository likeRepository;
    private final PostConverter postConverter;

    public PostListRes execute() {
        List<Post> posts = likeRepository.findByLikesSizeGreaterThan50();

        List<PostRes> res = posts.stream()
                .map(postConverter::toListDto)
                .toList();

        return PostListRes.builder()
                .posts(res)
                .build();
    }

}
