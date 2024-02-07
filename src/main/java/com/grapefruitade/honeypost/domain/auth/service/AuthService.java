package com.grapefruitade.honeypost.domain.auth.service;

import com.grapefruitade.honeypost.domain.auth.dto.LoginRequestDto;
import com.grapefruitade.honeypost.domain.auth.dto.RegisterRequestDto;
import com.grapefruitade.honeypost.domain.auth.dto.TokenDto;
import com.grapefruitade.honeypost.domain.auth.dto.TokenRequestDto;
import com.grapefruitade.honeypost.domain.auth.entity.RefreshToken;
import com.grapefruitade.honeypost.domain.auth.exception.ExistUsernameException;
import com.grapefruitade.honeypost.domain.auth.exception.TokenNotValidException;
import com.grapefruitade.honeypost.domain.auth.exception.UserNotFoundException;
import com.grapefruitade.honeypost.domain.auth.repository.RefreshTokenRepository;
import com.grapefruitade.honeypost.domain.user.dto.UserResponseDto;
import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.domain.user.repository.UserRepository;
import com.grapefruitade.honeypost.global.security.jwt.TokenProvider;
import com.grapefruitade.honeypost.global.security.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserUtil userUtil;

    public UserResponseDto register(RegisterRequestDto registerDto){
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new ExistUsernameException();
        }

        User user = registerDto.user(passwordEncoder);
        return UserResponseDto.userResponseDto(userRepository.save(user));
    }

    public TokenDto login(LoginRequestDto loginRequestDto){

        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }

    public TokenDto refresh (TokenRequestDto tokenRequestDto){

        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new TokenNotValidException();
        }

        Authentication authentication =
                tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
        RefreshToken refreshToken =
                refreshTokenRepository.findById(authentication.getName())
                        .orElseThrow(() -> new NullPointerException("로그아웃 된 사용자입니다."));
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new TokenNotValidException();
        }
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);
        return tokenDto;
    }


    public void logout() {

        User user = userUtil.currentUser();
        RefreshToken refreshToken = refreshTokenRepository.findById(String.valueOf(user.getId()))
                .orElseThrow(() -> new UserNotFoundException());
        refreshTokenRepository.delete(refreshToken);


    }


}
