package com.grapefruitade.honeypost.domain.post.controller;

import com.grapefruitade.honeypost.domain.like.dto.PostId;
import com.grapefruitade.honeypost.domain.like.service.LikeService;
import com.grapefruitade.honeypost.domain.post.Category;
import com.grapefruitade.honeypost.domain.post.dto.*;
import com.grapefruitade.honeypost.domain.post.service.PostService;
import com.grapefruitade.honeypost.global.security.auth.PrincipalDetails;
import com.grapefruitade.honeypost.global.util.UserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<String> write(@RequestBody WritePost write) {
        postService.writePost(write, userUtil.currentUser());
        return ResponseEntity.status(HttpStatus.OK).body("글 작성이 완료되었습니다.");
    }

    @PostMapping(value = "/preview", consumes = {"multipart/form-data"})
    public ResponseEntity<String> previewImage(@Valid @RequestPart(required = false) MultipartFile previewImage, @RequestPart Long id) {
        if (previewImage != null) {
            postService.uploadPreviewImage(previewImage, id);
        }
        return ResponseEntity.status(HttpStatus.OK).body("썸네일이 추가 되었습니다.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modify(@PathVariable Long id, @RequestBody ModifyPost modify) {
        postService.modifyPost(id, modify);
        return ResponseEntity.status(HttpStatus.OK).body("글 수정이 완료되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).body("글 삭제가 완료되었습니다.");
    }

    @GetMapping("/list")
    public ResponseEntity<List<InfoPost>> listPost(@RequestParam Category category) {
        return ResponseEntity.ok(postService.postList(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetails> infoPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.info(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<InfoPost>> searchPost(@RequestParam String keyword) {
        return ResponseEntity.ok(postService.searchPost(keyword));
    }

    @GetMapping("/hot topic")
    public ResponseEntity<List<InfoPost>> hotTopic() {
        return ResponseEntity.ok(postService.hotTopic());
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<String> like(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(likeService.toggleLike(id, userUtil.currentUser()));
    }
}
