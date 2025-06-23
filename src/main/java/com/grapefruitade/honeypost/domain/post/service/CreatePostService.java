package com.grapefruitade.honeypost.domain.post.service;

import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.presentation.dto.req.CreatePostReq;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.global.annotation.ServiceWithTransaction;
import com.grapefruitade.honeypost.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@ServiceWithTransaction
@RequiredArgsConstructor
public class CreatePostService {
    private final PostRepository postRepository;
    private final UserUtil userUtil;

    public Long execute(CreatePostReq createPostReq) {
        User user = userUtil.currentUser();

        Post post = Post.builder()
                .title(createPostReq.getTitle())
                .content(createPostReq.getContent())
                .category(createPostReq.getCategory())
                .ott(createPostReq.getOtt())
                .book(createPostReq.getBook())
                .author(user)
                .build();

        postRepository.save(post);

        return post.getId();
    }
}
