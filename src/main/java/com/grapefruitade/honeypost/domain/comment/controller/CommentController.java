package com.grapefruitade.honeypost.domain.comment.controller;


import com.grapefruitade.honeypost.domain.comment.dto.WriteCommentDto;
import com.grapefruitade.honeypost.domain.comment.service.CommentService;
import com.grapefruitade.honeypost.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/write")
    public ResponseEntity<String> write(@PathVariable Long postId, @RequestBody WriteCommentDto writeCommentDto) {
        commentService.writeComment(postId, writeCommentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("댓글 작성이 완료되었습니다.");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> delete(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("댓글 삭제가 완료되었습니다.");
    }
}
