package com.grapefruitade.honeypost.domain.post.service;

import com.grapefruitade.honeypost.domain.like.repository.LikeRepository;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.PostListRes;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.PostRes;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.post.util.PostConverter;
import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.global.annotation.ServiceWithReadOnlyTransaction;
import com.grapefruitade.honeypost.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ServiceWithReadOnlyTransaction
@RequiredArgsConstructor
public class SearchPostService {
    private final PostRepository postRepository;
    private final PostConverter postConverter;
    private final LikeRepository likeRepository;
    private final UserUtil userUtil;

    public PostListRes execute(String keyword) {
        User user = userUtil.currentUser();
        List<Post> posts = postRepository.findByTitleContainingOrContentContaining(keyword, keyword);

        List<PostRes> res = posts.stream()
                .map(p-> postConverter.toListDto(p,likeRepository.existsByUserIdAndPostId(user.getId(), p.getId())))
                .toList();

        return PostListRes.builder()
                .posts(res)
                .build();
    }
}
