package com.grapefruitade.honeypost.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
@Getter
public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN;
    @Override
    public String getAuthority() {
        return name();
    }
}
