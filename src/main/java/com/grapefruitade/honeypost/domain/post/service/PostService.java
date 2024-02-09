package com.grapefruitade.honeypost.domain.post.service;

import com.grapefruitade.honeypost.domain.image.entity.Image;
import com.grapefruitade.honeypost.domain.image.repository.ImageRepository;
import com.grapefruitade.honeypost.domain.image.util.ImageUtil;
import com.grapefruitade.honeypost.domain.post.Category;
import com.grapefruitade.honeypost.domain.post.dto.InfoPost;
import com.grapefruitade.honeypost.domain.post.dto.ModifyPost;
import com.grapefruitade.honeypost.domain.post.dto.PostDetails;
import com.grapefruitade.honeypost.domain.post.dto.WritePost;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.global.exception.CustomException;
import com.grapefruitade.honeypost.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final ImageUtil imageUtil;

    @Transactional
    public void writePost(WritePost writePost, List<MultipartFile> images) {
        Post post = Post.builder()
                .title(writePost.getTitle())
                .content(writePost.getContent())
                .category(writePost.getCategory())
                .ott(writePost.getOtt())
                .book(writePost.getBook())
                .build();

        imageUtil.saveImages(images, post);

        postRepository.save(post);
    }


    @Transactional
    public void modifyPost(Long id, ModifyPost modify) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INVALID_POST));

        post.modifyPost(modify.getTitle(), modify.getContent());
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INVALID_POST));

        postRepository.delete(post);
    }

    @Transactional
    public List<InfoPost> postList(Category category) {
        List<Post> list = postRepository.findByCategory(category);

        return list.stream()
                .map(this::infoPost)
                .collect(Collectors.toList());
    }

    private InfoPost infoPost(Post post) {
        return InfoPost.builder()
                .postId(post.getId())
                .author(post.getAuthor())
                .title(post.getTitle())
                .content(post.getContent())
                .likes(post.getLikes().size())
                .build();
    }

    @Transactional
    public PostDetails info(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_POST));
        List<Image> images = imageRepository.findByPostId(id);

        return PostDetails.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .likes(post.getLikes().size())
                .images(images.stream().map(image -> imageUrl(image.getSaveName())).toList())
                .build();
    }

    private String imageUrl(String saveName) {
        return "/images/" + saveName;
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
        List<Post> result = postRepository.findByLikesSizeGreaterThan50();

        return result.stream()
                .map(this::infoPost)
                .collect(Collectors.toList());
    }

}
