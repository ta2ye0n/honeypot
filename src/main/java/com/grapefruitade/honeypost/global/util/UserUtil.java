package com.grapefruitade.honeypost.global.util;

import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.domain.user.repository.UserRepository;
import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public User currentUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
