package com.grapefruitade.honeypost.domain.like.entity;

import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

    public LikeEntity(User user, Post post) {
        this.userId = user.getId();
        this.postId = post.getId();
    }
}
