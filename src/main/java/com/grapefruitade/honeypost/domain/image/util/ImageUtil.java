package com.grapefruitade.honeypost.domain.image.util;

import com.grapefruitade.honeypost.domain.image.dto.ImageDto;
import com.grapefruitade.honeypost.domain.image.entity.Image;
import com.grapefruitade.honeypost.domain.image.repository.ImageRepository;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
public class ImageUtil {
    private final ImageRepository imageRepository;

    // 게시글 이미지들
    public List<Long> saveImages(List<MultipartFile> images, Post post) {
        List<Long> saveImageId = new ArrayList<>();

        if (images.size() > 7) {
            throw new CustomException(ErrorCode.MAXIMUM_IMAGES_EXCEEDED);
        }

        validImageFiles(images);

        for (MultipartFile image : images) {
            ImageDto imageDto = savedImage(image);

            Image save = imageRepository.save(imageDto.toEntity(post));
            saveImageId.add(save.getId());
        }

        return saveImageId;
    }

    // 썸네일
    public Long saveImage(MultipartFile image, Post post) {
        validImageFile(image);
        ImageDto imageDto = savedImage(image);

        Image save = imageRepository.save(imageDto.toEntity(post));
        post.uploadPreviewUrl(save.getSaveName());

        return save.getId();
    }

    // 이미지 확장자 검사
    private void validImageFiles(List<MultipartFile> images) {
        List<String> imageExtensions = List.of(".png", ".jpg", ".jpeg");

        for (MultipartFile image: images) {
            String originalName = image.getOriginalFilename();
            if (image != null &&
                    originalName.toLowerCase().endsWith(imageExtensions.toString())) {
                throw new CustomException(ErrorCode.INVALID_EXTENSION);
            }
        }
    }

    private void validImageFile(MultipartFile image) {
        List<String> imageExtensions = List.of(".png", ".jpg", ".jpeg");

        String originalName = image.getOriginalFilename();
        if (image != null &&
                originalName.toLowerCase().endsWith(imageExtensions.toString())) {
            throw new CustomException(ErrorCode.INVALID_EXTENSION);
        }
    }

    // 이미지 저장 + 객체 생성
    @SneakyThrows
    private ImageDto savedImage(MultipartFile image) {
        File file = new File(System.getProperty("user.dir") + File.separator + "/src/main/resources/static/images/");
        if (!file.exists()) {
            file.mkdirs();
        }

        UUID uuid = UUID.randomUUID();
        String originalFileName = image.getOriginalFilename();

        String savedFileName = uuid.toString() + "_" + originalFileName;

        file = new File(file + File.separator + savedFileName);

        image.transferTo(file);

        return ImageDto.builder()
                .originalName(image.getOriginalFilename())
                .saveName(savedFileName)
                .url(file.getPath())
                .build();
    }
}
