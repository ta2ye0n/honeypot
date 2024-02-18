package com.grapefruitade.honeypost.domain.comment.dto;
import com.grapefruitade.honeypost.domain.comment.entity.Comment;
import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.user.entity.User;
import lombok.*;
@Getter
@NoArgsConstructor
public class WriteCommentDto {

    private String content;

    public Comment toEntity(User user, Post post){

        return Comment.builder()
                .content(this.content)
                .author(user)
                .post(post)
                .build();
    }

}

