package com.grapefruitade.honeypost.domain.comment.repository;

import com.grapefruitade.honeypost.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository <Comment, Long> {

}
