package com.grapefruitade.honeypost.domain.auth.dto;

import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.domain.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    private String username;
    private String password;
    private String nickname;

    public User user(PasswordEncoder passwordEncoder){
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.ROLE_USER)
                .nickname(nickname)
                .build();
    }




}
