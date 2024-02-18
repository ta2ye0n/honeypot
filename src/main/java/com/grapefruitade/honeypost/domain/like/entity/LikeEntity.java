package com.grapefruitade.honeypost.domain.like.entity;

import com.grapefruitade.honeypost.domain.post.entity.Post;
import com.grapefruitade.honeypost.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public LikeEntity(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
