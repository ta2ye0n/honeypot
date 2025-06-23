package com.grapefruitade.honeypost.domain.user.service;

import com.grapefruitade.honeypost.domain.comment.repository.CommentRepository;
import com.grapefruitade.honeypost.domain.like.repository.LikeRepository;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.InfoPostRes;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.user.presentation.dto.res.UserPostInfoListRes;
import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserUtil userUtil;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true, rollbackFor = {Exception.class})
    public UserPostInfoListRes getMyPosts() {
        User user = userUtil.currentUser();

        List<Post> userPost = postRepository.findPostByAuthor(user);

        List<InfoPostRes> infoPosts = userPost.stream()
                .map(post -> InfoPostRes.builder()
                        .postId(post.getId())
                        .author(post.getAuthor().getUsername())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .likes(likeRepository.countByPostId(post.getId()))
                        .comments(commentRepository.countByPost(post))
                        .previewImage(post.getPreviewUrl())
                        .build())
                .collect(Collectors.toList());

        return new UserPostInfoListRes(user.getNickname(), infoPosts);
    }
}
