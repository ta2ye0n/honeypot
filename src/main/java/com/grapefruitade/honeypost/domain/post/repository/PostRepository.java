package com.grapefruitade.honeypost.domain.post.repository;

import com.grapefruitade.honeypost.domain.post.entity.enums.Category;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);

    List<Post> findPostByAuthor(User user);

}
