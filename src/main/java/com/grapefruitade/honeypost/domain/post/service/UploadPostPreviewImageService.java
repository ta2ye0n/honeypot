package com.grapefruitade.honeypost.domain.post.service;

import com.grapefruitade.honeypost.domain.image.service.ImageS3Service;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.post.exception.NotFoundPostException;
import com.grapefruitade.honeypost.domain.post.exception.UserNotSameException;
import com.grapefruitade.honeypost.domain.post.repository.PostRepository;
import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.global.annotation.ServiceWithTransaction;
import com.grapefruitade.honeypost.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@ServiceWithTransaction
@RequiredArgsConstructor
public class UploadPostPreviewImageService {
    private final PostRepository postRepository;
    private final ImageS3Service imageS3Service;
    private final UserUtil userUtil;

    public void execute(Long id, MultipartFile image) {
        User user = userUtil.currentUser();
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);

        if (user != post.getAuthor()) {
            throw new UserNotSameException();
        }

        String url = imageS3Service.execute(image).getImageUrl();

        post.uploadPreviewUrl(url);

        postRepository.save(post);
    }
}
