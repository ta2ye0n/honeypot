
package com.grapefruitade.honeypost.domain.comment.service;

import com.grapefruitade.honeypost.domain.comment.presentation.dto.req.EditCommentReq;
import com.grapefruitade.honeypost.domain.comment.presentation.dto.req.CreateCommentReq;
import com.grapefruitade.honeypost.domain.comment.entity.Comment;
import com.grapefruitade.honeypost.domain.comment.exception.NotFoundCommentException;
import com.grapefruitade.honeypost.domain.post.exception.NotFoundPostException;
import com.grapefruitade.honeypost.domain.post.exception.UserNotSameException;
import com.grapefruitade.honeypost.domain.comment.repository.CommentRepository;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.user.entity.User;
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

    public void writeComment(Long id, CreateCommentReq writeComment) {

        User user = userUtil.currentUser();
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);

        Comment comment = writeComment.toEntity(user, post);

        commentRepository.save(comment);

    }

    public void deleteComment(Long id) {
        User user = userUtil.currentUser();

        Comment comment = commentRepository
                .findById(id).orElseThrow(NotFoundCommentException::new);

        isSameUser(user.getUsername(), comment.getAuthor().getUsername());

        commentRepository.delete(comment);

    }

    public void modifyComment(Long id, EditCommentReq modify) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(NotFoundCommentException::new);

        User user = userUtil.currentUser();

        isSameUser(user.getUsername(), comment.getAuthor().getUsername());

        comment.updateComment(modify.getComment());
    }

    private void isSameUser (String userName, String commentUser) {
        if (!userName.equals(commentUser)) {
            throw  new UserNotSameException();
        }
    }
}
