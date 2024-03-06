package com.grapefruitade.honeypost.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.grapefruitade.honeypost.domain.image.entity.Image;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageS3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    public Image uploadImage (MultipartFile image, Post post) {
        String originName = image.getOriginalFilename();
        String storedImagePath = savedImage(image);

        Image newImage = Image.builder()
                .originalName(originName)
                .saveName(storedImagePath)
                .post(post)
                .build();

        post.uploadPreviewUrl(storedImagePath);
        return newImage;
    }

    private String changedImageName(String originName) {
        String random = UUID.randomUUID().toString();
        return random + originName;
    }

    private void validImageFile (String originalName) {
        List<String> imageExtensions = List.of(".png", ".jpg", ".jpeg");

        if (originalName.toLowerCase().endsWith(imageExtensions.toString())) {
            throw new CustomException(ErrorCode.INVALID_EXTENSION);
        }
    }

    private String savedImage (MultipartFile image) {
        String originalName = image.getOriginalFilename();
        validImageFile(originalName);
        String ext = originalName.substring(originalName.indexOf("."));

        String changedName = changedImageName(originalName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/"+ext);

        try {
            PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(
                    bucketName, changedName, image.getInputStream(), metadata
            ).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }

        return amazonS3.getUrl(bucketName, changedName).toString();
    }
}
