package com.grapefruitade.honeypost.domain.post.controller;

import com.grapefruitade.honeypost.domain.post.dto.ModifyPost;
import com.grapefruitade.honeypost.domain.post.dto.WritePost;
import com.grapefruitade.honeypost.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @PostMapping(value = "/write", consumes = {"multipart/form-data"})
    public ResponseEntity<String> write(@Valid @RequestPart List<MultipartFile> images, @RequestPart WritePost write) {
        postService.writePost(write, images);
        return ResponseEntity.status(HttpStatus.OK).body("글 작성이 완료되었습니다.");
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> modify(@PathVariable Long id, @RequestBody ModifyPost modify) {
        postService.modifyPost(id, modify);
        return ResponseEntity.status(HttpStatus.OK).body("글 수정이 완료되었습니다.");
    }

}
