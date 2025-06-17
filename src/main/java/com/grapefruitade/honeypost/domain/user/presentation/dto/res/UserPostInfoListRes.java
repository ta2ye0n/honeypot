package com.grapefruitade.honeypost.domain.user.dto.res;


import com.grapefruitade.honeypost.domain.post.presentation.dto.res.InfoPostRes;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserPostInfoListRes {
    private final List<InfoPostRes> infoPosts;
}