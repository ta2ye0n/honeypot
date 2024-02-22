package com.grapefruitade.honeypost.domain.user.service;

import com.grapefruitade.honeypost.domain.comment.repository.CommentRepository;
import com.grapefruitade.honeypost.domain.like.repository.LikeRepository;
import com.grapefruitade.honeypost.domain.post.dto.InfoPost;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserUtil userUtil;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true, rollbackFor = {Exception.class})
    public List<InfoPost> getMyPost(){

        User user = userUtil.currentUser();

        List<Post> userPost = postRepository.findPostByAuthor(user);

        List<InfoPost> infoPosts = new ArrayList<>();

        for (final Post post : userPost) {
            infoPosts.add(InfoPost.builder()
                    .author(post.getAuthor().getUsername())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .likes(likeRepository.countByPost(post))
                    .comments(commentRepository.countByPost(post))
                    .previewImage(post.getPreviewUrl())
                    .build());
        }
         return infoPosts;
    }
}