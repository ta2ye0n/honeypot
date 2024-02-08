package com.grapefruitade.honeypost.domain.post.service;

import com.grapefruitade.honeypost.domain.image.dto.ImageDto;
import com.grapefruitade.honeypost.domain.image.entity.Image;
import com.grapefruitade.honeypost.domain.image.repository.ImageRepository;
import com.grapefruitade.honeypost.domain.post.Category;
import com.grapefruitade.honeypost.domain.post.dto.InfoPost;
import com.grapefruitade.honeypost.domain.post.dto.ModifyPost;
import com.grapefruitade.honeypost.domain.post.dto.PostInfo;
import com.grapefruitade.honeypost.domain.post.dto.WritePost;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.global.exception.CustomException;
import com.grapefruitade.honeypost.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public void writePost(WritePost writePost, List<MultipartFile> images) {
        Post post = Post.builder()
                .title(writePost.getTitle())
                .content(writePost.getContent())
                .category(writePost.getCategory())
                .ott(writePost.getOtt())
                .book(writePost.getBook())
                .build();

        if (images != null && !images.isEmpty()) {
            if (images.size() > 7) {
                throw new CustomException(ErrorCode.MAXIMUM_IMAGES_EXCEEDED);
            }
            saveImages(images, post);
        }

        postRepository.save(post);
    }

    @SneakyThrows
    private List<Long> saveImages(List<MultipartFile> images, Post post) {
        List<Long> saveImageId = new ArrayList<>();

        for (MultipartFile image : images) {
            if (image != null &&
                    !image.getOriginalFilename().toLowerCase().endsWith(".png") &&
                    !image.getOriginalFilename().toLowerCase().endsWith(".jpg") &&
                    !image.getOriginalFilename().toLowerCase().endsWith(".jpeg")) {
                throw new CustomException(ErrorCode.INVALID_EXTENSION);
            }

            File file = new File(System.getProperty("user.dir") + File.separator + "/src/main/resources/static/images/");
            if (!file.exists()) {
                file.mkdirs();
            }

            UUID uuid = UUID.randomUUID();
            String originalFileName = image.getOriginalFilename();

            String savedFileName = uuid.toString() + "_" + originalFileName;

            file = new File(file + File.separator + savedFileName);

            image.transferTo(file);

            ImageDto imageDto = ImageDto.builder()
                    .originalName(image.getOriginalFilename())
                    .saveName(savedFileName)
                    .url(file.getPath())
                    .build();

            Image save = imageRepository.save(imageDto.toEntity(post));
            saveImageId.add(save.getId());
        }

        return saveImageId;
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
    public PostInfo info(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_POST));


        return PostInfo.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .likes(post.getLikes().size())
                .images(post.getImages().stream().map(image -> imageUrl(image.getSaveName())).toList())
                .build();
    }

    private String imageUrl(String saveName) {
        return "/images/" + saveName;
    }

    @Transactional
    public List<InfoPost> searchPost(String keyword) {
        List<Post> allPost = postRepository.findAll();
        List<Post> result = new ArrayList<>();

        for (Post post : allPost) {
            if (post.getContent().contains(keyword) ||
                    post.getTitle().contains(keyword)) {
                result.add(post);
            }
        }

        return result.stream()
                .map(this::infoPost)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<InfoPost> hotTopic() {
        List<Post> all = postRepository.findAll();
        List<Post> result = new ArrayList<>();

        for (Post post : all) {
            if (post.getLikes().size() >= 50) {
                result.add(post);
            }
        }

        return result.stream()
                .map(this::infoPost)
                .collect(Collectors.toList());
    }
}
