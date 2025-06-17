package com.grapefruitade.honeypost.domain.user.dto.res;

import com.grapefruitade.honeypost.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserInfoRes {

    private String username;
    private String nickname;
    private String password;

    public static UserInfoRes userResponseDto(User user){
        return new UserInfoRes(user.getUsername(), user.getNickname(), user.getPassword());
    }
}
