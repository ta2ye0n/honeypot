package com.grapefruitade.honeypost.domain.comment.presentation;


import com.grapefruitade.honeypost.domain.comment.presentation.dto.req.EditCommentReq;
import com.grapefruitade.honeypost.domain.comment.presentation.dto.req.CreateCommentReq;
import com.grapefruitade.honeypost.domain.comment.service.CommentService;
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
    public ResponseEntity<String> write(@PathVariable ("post_id") Long postId, @RequestBody CreateCommentReq writeCommentDto) {
        commentService.writeComment(postId, writeCommentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("댓글 작성이 완료되었습니다.");
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<String> delete(@PathVariable ("comment_id") Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("댓글 삭제가 완료되었습니다.");
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<String> modify (@PathVariable("comment_id") Long commentId, @RequestBody EditCommentReq modifyComment) {
        commentService.modifyComment(commentId, modifyComment);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("댓글 수정이 완료되었습니다.");
    }
}
