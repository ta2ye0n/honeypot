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

    @PostMapping("/{post_id}/write")
    public ResponseEntity<String> write(@PathVariable Long post_id, @RequestBody WriteCommentDto writeCommentDto) {
        commentService.writeComment(post_id, writeCommentDto);
        return ResponseEntity.status(HttpStatus.OK).body("댓글 작성이 완료되었습니다.");
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<String> delete(@PathVariable Long comment_id){
        commentService.deleteComment(comment_id);
        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제가 완료되었습니다.");
    }
}
