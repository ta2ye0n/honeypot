package com.grapefruitade.honeypost.domain.post.service;

import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.exception.NotFoundPostException;
import com.grapefruitade.honeypost.domain.post.exception.UserNotSameException;
import com.grapefruitade.honeypost.domain.post.presentation.dto.req.EditPostReq;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.global.annotation.ServiceWithTransaction;
import com.grapefruitade.honeypost.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransaction
public class EditPostService {
    private final PostRepository postRepository;
    private final UserUtil userUtil;

    public void execute(Long id, EditPostReq editPostReq) {
        User user = userUtil.currentUser();
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);

        if (post.getAuthor() != user) {
            throw new UserNotSameException();
        }

        post.editPost(editPostReq.getTitle(), editPostReq.getContent());

        postRepository.save(post);
    }
}
