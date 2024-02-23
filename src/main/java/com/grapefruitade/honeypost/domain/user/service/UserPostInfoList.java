package com.grapefruitade.honeypost.domain.user.service;


import com.grapefruitade.honeypost.domain.post.dto.InfoPost;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserPostInfoList {
    private final List<InfoPost> infoPosts;
}