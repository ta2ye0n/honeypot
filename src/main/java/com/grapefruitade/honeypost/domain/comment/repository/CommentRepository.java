package com.grapefruitade.honeypost.domain.comment.repository;

import com.grapefruitade.honeypost.domain.comment.entity.Comment;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository <Comment, Long> {

    List<Comment> findByPostId (Long postId);

    Integer countByPost (Post post);
}
