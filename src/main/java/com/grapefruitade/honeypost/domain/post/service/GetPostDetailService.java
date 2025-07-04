package com.grapefruitade.honeypost.domain.post.service;

import com.grapefruitade.honeypost.domain.comment.entity.Comment;
import com.grapefruitade.honeypost.domain.comment.presentation.dto.res.CommentListRes;
import com.grapefruitade.honeypost.domain.comment.presentation.dto.res.CommentRes;
import com.grapefruitade.honeypost.domain.comment.repository.CommentRepository;
import com.grapefruitade.honeypost.domain.comment.util.CommentConverter;
import com.grapefruitade.honeypost.domain.like.repository.LikeRepository;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.exception.NotFoundPostException;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.PostDetailsRes;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.post.util.PostConverter;
import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.global.annotation.ServiceWithReadOnlyTransaction;
import com.grapefruitade.honeypost.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ServiceWithReadOnlyTransaction
@RequiredArgsConstructor
public class GetPostDetailService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostConverter postConverter;
    private final CommentConverter commentConverter;
    private final LikeRepository likeRepository;
    private final UserUtil userUtil;

    public PostDetailsRes execute(Long postId) {
        User user = userUtil.currentUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundPostException::new);

        List<CommentRes> comments = commentRepository.findByPostId(postId).stream()
                .map(commentConverter::toDto)
                .toList();

        CommentListRes res = CommentListRes.builder()
                .comments(comments)
                .build();

        return postConverter
                .toDto(post, res,
                    likeRepository.existsByUserIdAndPostId(user.getId(), postId),
                    commentRepository.countByPost(post)
                );
    }

}
