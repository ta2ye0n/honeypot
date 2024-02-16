package com.grapefruitade.honeypost.domain.comment.dto;
import lombok.*;
@Getter
@NoArgsConstructor
public class WriteCommentDto {

    private String content;
    private String author;
    private Long post_id;

}

