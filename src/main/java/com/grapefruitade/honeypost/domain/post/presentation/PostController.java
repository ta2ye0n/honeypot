package com.grapefruitade.honeypost.domain.post.presentation;

import com.grapefruitade.honeypost.domain.like.service.LikeService;
import com.grapefruitade.honeypost.domain.post.presentation.dto.req.EditPostReq;
import com.grapefruitade.honeypost.domain.post.presentation.dto.req.CreatePostReq;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.PostListRes;
import com.grapefruitade.honeypost.domain.post.presentation.dto.res.PostDetailsRes;
import com.grapefruitade.honeypost.domain.post.entity.enums.Category;
import com.grapefruitade.honeypost.domain.post.service.*;
import com.grapefruitade.honeypost.global.util.UserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final LikeService likeService;
    private final CreatePostService createPostService;
    private final DeletePostService deletePostService;
    private final EditPostService editPostService;
    private final GetHopTopicPostService getHopTopicPostService;
    private final GetPostDetailService getPostDetailService;
    private final GetPostListService getPostListService;
    private final SearchPostService searchPostService;
    private final UploadPostPreviewImageService uploadPostPreviewImageService;

    @PostMapping(value = "/write")
    public ResponseEntity<Long> write(@RequestBody CreatePostReq write) {
        Long id = createPostService.execute(write);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PostMapping(value = "/preview/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Void> previewImage(@Valid @RequestPart(required = false) MultipartFile previewImage, @PathVariable Long id) {
        if (previewImage != null) {
            uploadPostPreviewImageService.execute(id, previewImage);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> edit(@PathVariable Long id, @RequestBody EditPostReq modify) {
        editPostService.execute(id, modify);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deletePostService.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity<PostListRes> listPost(@RequestParam Category category) {
        PostListRes res = getPostListService.execute(category);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailsRes> infoPost(@PathVariable Long id) {
        PostDetailsRes res = getPostDetailService.execute(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/search")
    public ResponseEntity<PostListRes> searchPost(@RequestParam String keyword) {
        PostListRes res = searchPostService.execute(keyword);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/topic")
    public ResponseEntity<PostListRes> hotTopic() {
        PostListRes res = getHopTopicPostService.execute();
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/like/{id}")
    public ResponseEntity<Void> like(@Valid @PathVariable Long id) {
        likeService.execute(id);
        return ResponseEntity.ok().build();
    }
}
