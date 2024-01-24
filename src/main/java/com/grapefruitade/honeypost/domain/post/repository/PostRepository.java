package com.grapefruitade.honeypost.domain.post.repository;

import com.grapefruitade.honeypost.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
