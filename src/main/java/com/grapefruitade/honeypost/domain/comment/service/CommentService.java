
package com.grapefruitade.honeypost.domain.comment.service;

import com.grapefruitade.honeypost.domain.comment.dto.ModifyComment;
import com.grapefruitade.honeypost.domain.comment.dto.WriteCommentDto;
import com.grapefruitade.honeypost.domain.comment.entity.Comment;
import com.grapefruitade.honeypost.domain.comment.exception.CommentNotFound;
import com.grapefruitade.honeypost.domain.comment.exception.PostNotFound;
import com.grapefruitade.honeypost.domain.comment.exception.UserNotSame;
import com.grapefruitade.honeypost.domain.comment.repository.CommentRepository;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;
import com.grapefruitade.honeypost.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserUtil userUtil;

    public void writeComment(Long id, WriteCommentDto writeComment) {

        User user = userUtil.currentUser();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());

        Comment comment = writeComment.toEntity(user, post);

        commentRepository.save(comment);

    }

    public void deleteComment(Long id) {

        User user = userUtil.currentUser();

        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new CommentNotFound());

        if (user.getUsername() != (comment.getAuthor().getUsername())) {
            throw new UserNotSame();
        }

        commentRepository.delete(comment);

    }

    public void modifyComment(Long id, ModifyComment modify) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFound());

        comment.updateComment(modify.getComment());
    }
}
