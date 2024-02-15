package com.grapefruitade.honeypost.domain.like.repository;

import com.grapefruitade.honeypost.domain.like.entity.LikeEntity;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    boolean existsByUserAndPost(User user, Post post);

    void deleteByUserAndPost (User user, Post post);
}
