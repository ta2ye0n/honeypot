package com.grapefruitade.honeypost.domain.auth.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto
{

    private String grantType; // JWT에 대한 인증 타입. 여기서는 Bearer를 사용한다.
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
    private Long refreshTokenExpiresIn;

}
