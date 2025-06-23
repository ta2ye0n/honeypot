package com.grapefruitade.honeypost.domain.post.service;

import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.PostListRes;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.PostRes;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.post.util.PostConverter;
import com.grapefruitade.honeypost.global.annotation.ServiceWithReadOnlyTransaction;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ServiceWithReadOnlyTransaction
@RequiredArgsConstructor
public class SearchPostService {
    private final PostRepository postRepository;
    private final PostConverter postConverter;

    public PostListRes execute(String keyword) {
        List<Post> posts = postRepository.findByTitleContainingOrContentContaining(keyword, keyword);

        List<PostRes> res = posts.stream()
                .map(postConverter::toListDto)
                .toList();

        return PostListRes.builder()
                .posts(res)
                .build();
    }
}
