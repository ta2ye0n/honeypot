package com.grapefruitade.honeypost.domain.post.service;

import com.grapefruitade.honeypost.domain.image.service.ImageS3Service;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.exception.NotFoundPostException;
import com.grapefruitade.honeypost.domain.post.exception.UserNotSameException;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.global.annotation.ServiceWithTransaction;
import com.grapefruitade.honeypost.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@ServiceWithTransaction
@RequiredArgsConstructor
public class DeletePostService {
    private final PostRepository postRepository;
    private final UserUtil userUtil;
    private final ImageS3Service imageS3Service;

    public void execute(Long id) {
        User user = userUtil.currentUser();
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);

        if (user != post.getAuthor()) {
            throw new UserNotSameException();
        }

        imageS3Service.deleteFile(post.getPreviewUrl());
        postRepository.deleteById(post.getId());
    }
}
