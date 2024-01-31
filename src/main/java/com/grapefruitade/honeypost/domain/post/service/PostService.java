package com.grapefruitade.honeypost.domain.post.service;

import com.grapefruitade.honeypost.domain.image.dto.ImageDto;
import com.grapefruitade.honeypost.domain.image.entity.Image;
import com.grapefruitade.honeypost.domain.image.repository.ImageRepository;
import com.grapefruitade.honeypost.domain.post.dto.ModifyPost;
import com.grapefruitade.honeypost.domain.post.dto.WritePost;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.global.exception.CustomException;
import com.grapefruitade.honeypost.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public void writePost(WritePost writePost, List<MultipartFile> images) {
        if (images.size() > 7) {
            throw new CustomException(ErrorCode.MAXIMUM_IMAGES_EXCEEDED);
        }
        List<Long> imagesId = saveImages(images);

        Post post = Post.builder()
                .title(writePost.getTitle())
                .content(writePost.getContent())
                .category(writePost.getCategory())
                .ott(writePost.getOtt())
                .book(writePost.getBook())
                .build();

        postRepository.save(post);
    }

    @SneakyThrows
    private List<Long> saveImages(List<MultipartFile> images) {
        List<Long> saveImageId = new ArrayList<>();

        for (MultipartFile image : images) {
            if (image != null &&
                    !image.getOriginalFilename().toLowerCase().endsWith(".png") &&
                    !image.getOriginalFilename().toLowerCase().endsWith(".jpg") &&
                    !image.getOriginalFilename().toLowerCase().endsWith(".jpeg")) {
                throw new CustomException(ErrorCode.INVALID_EXTENSION);
            }

            File file = new File(System.getProperty("user.dir") + File.separator + "/src/main/resources/static/images/");

            String originalFileName = image.getOriginalFilename();

            UUID uuid = UUID.randomUUID();
            String savedFileName = uuid.toString() + "_" + originalFileName;

            File newFile = new File(file + savedFileName);

            image.transferTo(new File(String.valueOf(newFile)));

            ImageDto imageDto = ImageDto.builder()
                    .original_name(image.getOriginalFilename())
                    .save_name(savedFileName)
                    .path(newFile.getPath())
                    .build();

            Image save = imageRepository.save(imageDto.toEntity());
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
}
