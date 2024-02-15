package com.grapefruitade.honeypost.domain.like.service;

import com.grapefruitade.honeypost.domain.like.dto.PostId;
import com.grapefruitade.honeypost.domain.like.entity.LikeEntity;
import com.grapefruitade.honeypost.domain.like.repository.LikeRepository;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Transactional
    public String toggleLike(PostId postId, User user) {
        Post post = postRepository.findById(postId.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_POST));

        if (likeRepository.existsByUserAndPost(user, post)) {
            unlikePost(user, post);
            return "좋아요가 취소되었습니다.";
        } else {
            likePost(user, post);
            return "좋아요를 눌렀습니다.";
        }
    }

    private void likePost(User user, Post post) {
        LikeEntity like = new LikeEntity(user, post);
        likeRepository.save(like);
    }

    private void unlikePost(User user, Post post) {
        likeRepository.deleteByUserAndPost(user, post);
    }
}
