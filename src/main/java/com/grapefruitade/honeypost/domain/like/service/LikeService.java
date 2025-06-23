package com.grapefruitade.honeypost.domain.like.service;

import com.grapefruitade.honeypost.domain.like.entity.LikeEntity;
import com.grapefruitade.honeypost.domain.like.repository.LikeRepository;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.exception.NotFoundPostException;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.global.annotation.ServiceWithTransaction;
import com.grapefruitade.honeypost.global.util.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@ServiceWithTransaction
@AllArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserUtil userUtil;

    public void execute(Long postId) {
        User user = userUtil.currentUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundPostException::new);

        LikeEntity like = likeRepository.findByUserIdAndPostId(user.getId(), postId);
        if (like != null) {
            likeRepository.deleteByUserIdAndPostId(user.getId(), post.getId());
            post.unlike();
        } else {
            likeRepository.save(new LikeEntity(user, post));
            post.like();
        }
    }
}
