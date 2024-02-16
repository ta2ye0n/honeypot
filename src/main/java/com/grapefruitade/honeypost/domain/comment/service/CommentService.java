
package com.grapefruitade.honeypost.domain.comment.service;

import com.grapefruitade.honeypost.domain.comment.dto.WriteCommentDto;
import com.grapefruitade.honeypost.domain.comment.entity.Comment;
import com.grapefruitade.honeypost.domain.comment.exception.InvalidComment;
import com.grapefruitade.honeypost.domain.comment.exception.InvalidPost;
import com.grapefruitade.honeypost.domain.comment.repository.CommentRepository;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void writeComment(Long id, WriteCommentDto writeComment, User user) {

        Comment comment = Comment.builder()
                .content(writeComment.getContent())
                .post(postRepository.findById(id)
                        .orElseThrow(() -> new InvalidPost()))
                .author(user)
                .build();

        commentRepository.save(comment);
    }

    public void deleteComment(Long id){

        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new InvalidComment());

        commentRepository.delete(comment);

    }

}
