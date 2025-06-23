package com.grapefruitade.honeypost.domain.post.service;

import com.grapefruitade.honeypost.domain.post.entity.enums.Category;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.PostListRes;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.post.util.PostConverter;
import com.grapefruitade.honeypost.global.annotation.ServiceWithReadOnlyTransaction;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ServiceWithReadOnlyTransaction
@RequiredArgsConstructor
public class GetPostListService {
    private final PostRepository postRepository;
    private final PostConverter postConverter;

    public PostListRes execute(Category category) {
        var posts = postRepository.findByCategory(category);

        return PostListRes.builder()
                .posts(posts.stream()
                        .map(postConverter::toListDto)
                        .toList())
                .build();
    }
}
