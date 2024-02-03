package com.grapefruitade.honeypost.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenDto
{

    private String grantType; // JWT에 대한 인증 타입. 여기서는 Bearer를 사용한다.
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
}
