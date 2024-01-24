package com.grapefruitade.honeypost.domain.post.controller;

import com.grapefruitade.honeypost.domain.post.dto.WritePost;
import com.grapefruitade.honeypost.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/write")
    public ResponseEntity<String> write (@RequestBody WritePost writePost) {
        postService.writePost(writePost);
        return ResponseEntity.status(HttpStatus.OK).body("글작성이 완료되었습니다.");
    }
}
