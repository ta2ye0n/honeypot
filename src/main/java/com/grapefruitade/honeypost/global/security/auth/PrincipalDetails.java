package com.grapefruitade.honeypost.global.security.auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@ToString
@AllArgsConstructor
@Data
@Builder
public class PrincipalDetails implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private Collection<GrantedAuthority> role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }
    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}