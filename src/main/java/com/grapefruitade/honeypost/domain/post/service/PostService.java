package com.grapefruitade.honeypost.domain.post.service;

import com.grapefruitade.honeypost.domain.comment.presentation.dto.res.DetailCommentRes;
import com.grapefruitade.honeypost.domain.comment.entity.Comment;
import com.grapefruitade.honeypost.domain.comment.repository.CommentRepository;
import com.grapefruitade.honeypost.domain.image.service.ImageS3Service;
import com.grapefruitade.honeypost.domain.like.repository.LikeRepository;
import com.grapefruitade.honeypost.domain.post.entity.enums.Category;
import com.grapefruitade.honeypost.domain.post.exception.NotFoundPostException;
import com.grapefruitade.honeypost.domain.post.exception.UserNotSameException;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.InfoPostRes;
import com.grapefruitade.honeypost.domain.post.presentation.dto.req.EditPostReq;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.PostDetailsRes;
import com.grapefruitade.honeypost.domain.post.presentation.dto.req.CreatePostReq;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.user.entity.User;
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
    private final ImageS3Service imageS3Service;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final CommonUtil commonUtil;

    @Transactional(rollbackFor = {Exception.class})
    public Long writePost(CreatePostReq writePost, User user) {
        Post post = Post.builder()
                .title(writePost.getTitle())
                .content(writePost.getContent())
                .author(user)
                .category(writePost.getCategory())
                .ott(writePost.getOtt())
                .book(writePost.getBook())
                .build();

        postRepository.save(post);

        return post.getId();
    }

    @Transactional(rollbackFor = {Exception.class})
    public void uploadPreviewImage(MultipartFile image, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);

        imageS3Service.uploadImage(image, post);

    }


    @Transactional(rollbackFor = {Exception.class})
    public void modifyPost(Long id, EditPostReq modify, User user) {
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);
        isSameUser(user.getUsername(), post.getAuthor().getUsername());

        post.modifyPost(modify.getTitle(), modify.getContent());
        postRepository.save(post);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deletePost(Long id, User user) {
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);
        isSameUser(user.getUsername(), post.getAuthor().getUsername());

        postRepository.delete(post);
    }

    private void isSameUser (String userName, String commentUser) {
        if (!userName.equals(commentUser)) {
            throw new UserNotSameException();
        }
    }

    @Transactional
    public List<InfoPostRes> postList(Category category) {
        List<Post> list = postRepository.findByCategory(category);

        return list.stream()
                .map(this::infoPost)
                .collect(Collectors.toList());
    }

    private InfoPostRes infoPost(Post post) {
        String preview = post.getPreviewUrl() != null ? post.getPreviewUrl() : null;

        return InfoPostRes.builder()
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
    public PostDetailsRes info(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);
        List<Comment> comments = commentRepository.findByPostId(id);

        return PostDetailsRes.builder()
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

    private DetailCommentRes detailComment(Comment comment) {
        return DetailCommentRes.builder()
                .author(comment.getAuthor().getNickname())
                .comment(comment.getContent())
                .build();
    }

    @Transactional
    public List<InfoPostRes> searchPost(String keyword) {
        List<Post> result = postRepository
                .findByTitleContainingOrContentContaining(keyword, keyword);

        return result.stream()
                .map(this::infoPost)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<InfoPostRes> hotTopic() {
        List<Post> result = likeRepository.findByLikesSizeGreaterThan50();

        return result.stream()
                .map(this::infoPost)
                .collect(Collectors.toList());
    }

}
