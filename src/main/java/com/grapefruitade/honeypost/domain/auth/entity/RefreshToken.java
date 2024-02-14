package com.grapefruitade.honeypost.domain.auth.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@RedisHash(value = "refreshToken", timeToLive = 60)
public class RefreshToken {

    @Id
    private String token;
    private Long userid;
    private Long expiresAt;

}
