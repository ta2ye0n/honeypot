package com.grapefruitade.honeypost.domain.post.controller;

import com.grapefruitade.honeypost.domain.like.service.LikeService;
import com.grapefruitade.honeypost.domain.post.dto.req.EditPostReq;
import com.grapefruitade.honeypost.domain.post.dto.req.CreatePostReq;
import com.grapefruitade.honeypost.domain.post.dto.res.InfoPostRes;
import com.grapefruitade.honeypost.domain.post.dto.res.PostDetailsRes;
import com.grapefruitade.honeypost.domain.post.enums.Category;
import com.grapefruitade.honeypost.domain.post.service.PostService;
import com.grapefruitade.honeypost.global.util.UserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;
    private final UserUtil userUtil;

    @PostMapping(value = "/write")
    public ResponseEntity<Long> write(@RequestBody CreatePostReq write) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.writePost(write, userUtil.currentUser()));
    }

    @PostMapping(value = "/preview/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<String> previewImage(@Valid @RequestPart(required = false) MultipartFile previewImage, @PathVariable Long id) {
        if (previewImage != null) {
            postService.uploadPreviewImage(previewImage, id);
        }
        return ResponseEntity.status(HttpStatus.OK).body("썸네일이 추가 되었습니다.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modify(@PathVariable Long id, @RequestBody EditPostReq modify) {
        postService.modifyPost(id, modify, userUtil.currentUser());
        return ResponseEntity.status(HttpStatus.OK).body("글 수정이 완료되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        postService.deletePost(id, userUtil.currentUser());
        return ResponseEntity.status(HttpStatus.OK).body("글 삭제가 완료되었습니다.");
    }

    @GetMapping("/list")
    public ResponseEntity<List<InfoPostRes>> listPost(@RequestParam Category category) {
        return ResponseEntity.ok(postService.postList(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailsRes> infoPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.info(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<InfoPostRes>> searchPost(@RequestParam String keyword) {
        return ResponseEntity.ok(postService.searchPost(keyword));
    }

    @GetMapping("/hot topic")
    public ResponseEntity<List<InfoPostRes>> hotTopic() {
        return ResponseEntity.ok(postService.hotTopic());
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<String> like(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(likeService.toggleLike(id, userUtil.currentUser()));
    }
}
