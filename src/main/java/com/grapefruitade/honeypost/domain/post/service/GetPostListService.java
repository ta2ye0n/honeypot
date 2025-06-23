package com.grapefruitade.honeypost.domain.post.service;

import com.grapefruitade.honeypost.domain.like.repository.LikeRepository;
import com.grapefruitade.honeypost.domain.post.entity.enums.Category;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.PostListRes;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.post.util.PostConverter;
import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.global.annotation.ServiceWithReadOnlyTransaction;
import com.grapefruitade.honeypost.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@ServiceWithReadOnlyTransaction
@RequiredArgsConstructor
public class GetPostListService {
    private final PostRepository postRepository;
    private final PostConverter postConverter;
    private final LikeRepository likeRepository;
    private final UserUtil userUtil;

    public PostListRes execute(Category category) {
        User user = userUtil.currentUser();
        var posts = postRepository.findByCategory(category);

        return PostListRes.builder()
                .posts(posts.stream()
                        .map(p-> postConverter.toListDto(p,likeRepository.existsByUserIdAndPostId(user.getId(), p.getId())))
                        .toList())
                .build();
    }
}
