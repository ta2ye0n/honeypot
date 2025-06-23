package com.grapefruitade.honeypost.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.grapefruitade.honeypost.domain.image.entity.Image;
import com.grapefruitade.honeypost.domain.image.exception.ImageUploadFailedException;
import com.grapefruitade.honeypost.domain.image.exception.InvalidExtensionException;
import com.grapefruitade.honeypost.domain.image.exception.NotAllowedFileException;
import com.grapefruitade.honeypost.domain.image.presentation.dto.FileUploadRes;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageS3Service {

    @Value("${cloud.aws.s3.bucketName}")
    private String bucket;
    private final AmazonS3 amazonS3;

    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif", "image/svg+xml");

    public FileUploadRes execute(MultipartFile file) throws ImageUploadFailedException {

        String fileName = createFileName(file.getOriginalFilename());

        String contentType = file.getContentType();
        if (!ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new NotAllowedFileException();
        }

        ObjectMetadata objectMetadata = new ObjectMetadata();

        objectMetadata.setContentLength(file.getSize());

        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()) {

            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch(IOException e) {

            throw new ImageUploadFailedException();
        }

        String fileUrl = generateFileUrl(fileName);

        return FileUploadRes.builder()
                .imageUrl(fileUrl)
                .build();
    }

    public void deleteFile(String fileName) {

        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private String createFileName(String fileName) {

        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {

        try {

            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {

            throw new InvalidExtensionException();
        }
    }

    private String generateFileUrl(String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}
