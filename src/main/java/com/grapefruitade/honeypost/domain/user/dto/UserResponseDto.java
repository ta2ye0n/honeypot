package com.grapefruitade.honeypost.domain.user.dto;

import com.grapefruitade.honeypost.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResponseDto {

    private String username;
    private String nickname;
    private String password;

    public static UserResponseDto userResponseDto(User user){
        return new UserResponseDto(user.getUsername(), user.getNickname(), user.getPassword());
    }
}
