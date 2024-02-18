package com.grapefruitade.honeypost.domain.post.repository;

import com.grapefruitade.honeypost.domain.post.Category;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);
}
