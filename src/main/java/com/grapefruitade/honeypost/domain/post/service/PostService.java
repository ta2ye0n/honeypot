package com.grapefruitade.honeypost.domain.post.service;

import com.grapefruitade.honeypost.domain.comment.dto.DetailComment;
import com.grapefruitade.honeypost.domain.comment.entity.Comment;
import com.grapefruitade.honeypost.domain.comment.exception.UserNotSame;
import com.grapefruitade.honeypost.domain.comment.repository.CommentRepository;
import com.grapefruitade.honeypost.domain.image.util.ImageUtil;
import com.grapefruitade.honeypost.domain.like.repository.LikeRepository;
import com.grapefruitade.honeypost.domain.post.Category;
import com.grapefruitade.honeypost.domain.post.dto.InfoPost;
import com.grapefruitade.honeypost.domain.post.dto.ModifyPost;
import com.grapefruitade.honeypost.domain.post.dto.PostDetails;
import com.grapefruitade.honeypost.domain.post.dto.WritePost;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;
import com.grapefruitade.honeypost.global.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageUtil imageUtil;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final CommonUtil commonUtil;

    @Transactional(rollbackFor = {Exception.class})
    public void writePost(WritePost writePost, User user) {
        Post post = Post.builder()
                .title(writePost.getTitle())
                .content(writePost.getContent())
                .author(user)
                .category(writePost.getCategory())
                .ott(writePost.getOtt())
                .book(writePost.getBook())
                .build();

        postRepository.save(post);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void uploadPreviewImage(MultipartFile image, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_POST));

        imageUtil.saveImage(image, post);

    }


    @Transactional(rollbackFor = {Exception.class})
    public void modifyPost(Long id, ModifyPost modify, User user) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INVALID_POST));
        isSameUser(user.getUsername(), post.getAuthor().getUsername());

        post.modifyPost(modify.getTitle(), modify.getContent());
        postRepository.save(post);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deletePost(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INVALID_POST));
        isSameUser(user.getUsername(), post.getAuthor().getUsername());

        postRepository.delete(post);
    }

    private void isSameUser (String userName, String commentUser) {
        if (!userName.equals(commentUser)) {
            throw  new CustomException(ErrorCode.USER_NOT_SAME);
        }
    }

    @Transactional
    public List<InfoPost> postList(Category category) {
        List<Post> list = postRepository.findByCategory(category);

        return list.stream()
                .map(this::infoPost)
                .collect(Collectors.toList());
    }

    private InfoPost infoPost(Post post) {
        String preview = post.getPreviewUrl() != null ? imageUrl(post.getPreviewUrl()) : null;

        return InfoPost.builder()
                .postId(post.getId())
                .author(post.getAuthor().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .likes(likeRepository.countByPost(post))
                .comments(commentRepository.countByPost(post))
                .previewImage(preview)
                .build();
    }

    @Transactional(rollbackFor = {Exception.class})
    public PostDetails info(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_POST));
        List<Comment> comments = commentRepository.findByPostId(id);


        return PostDetails.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(commonUtil.markdown(post.getContent()))
                .author(post.getAuthor().getNickname())
                .likes(likeRepository.countByPost(post))
                .comments(comments.stream().map(this::detailComment).toList())
                .build();
    }

    private String imageUrl(String saveName) {
        return "/images/" + saveName;
    }

    private DetailComment detailComment(Comment comment) {
        return DetailComment.builder()
                .author(comment.getAuthor().getNickname())
                .comment(comment.getContent())
                .build();
    }

    @Transactional
    public List<InfoPost> searchPost(String keyword) {
        List<Post> result = postRepository.findByTitleContainingOrContentContaining(keyword, keyword);

        return result.stream()
                .map(this::infoPost)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<InfoPost> hotTopic() {
        List<Post> result = likeRepository.findByLikesSizeGreaterThan50();

        return result.stream()
                .map(this::infoPost)
                .collect(Collectors.toList());
    }

}
