package com.grapefruitade.honeypost.domain.user.presentation.dto.res;

import com.grapefruitade.honeypost.domain.post.presentation.dto.res.InfoPostRes;

import java.util.List;

public record UserPostInfoListRes(
        String nickname,
        List<InfoPostRes> infoPosts
) {
}