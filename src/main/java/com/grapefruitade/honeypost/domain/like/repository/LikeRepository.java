package com.grapefruitade.honeypost.domain.like.repository;

import com.grapefruitade.honeypost.domain.like.entity.LikeEntity;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);

    Integer countByPostId(Long postId);

    @Query("SELECT l.postId FROM LikeEntity l GROUP BY l.postId HAVING COUNT(l.postId) >= 50")
    List<Post> findByLikesSizeGreaterThan50();

    LikeEntity findByUserIdAndPostId(Long userId, Long postId);
}
