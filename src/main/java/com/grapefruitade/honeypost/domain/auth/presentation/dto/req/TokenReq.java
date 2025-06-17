package com.grapefruitade.honeypost.domain.auth.presentation.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenReq {

    private String accessToken;
    private String refreshToken;

}
