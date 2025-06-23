package com.grapefruitade.honeypost.domain.auth.service;

import com.grapefruitade.honeypost.domain.auth.presentation.dto.req.LoginReq;
import com.grapefruitade.honeypost.domain.auth.presentation.dto.req.RegisterReq;
import com.grapefruitade.honeypost.domain.auth.presentation.dto.res.TokenRes;
import com.grapefruitade.honeypost.domain.auth.entity.RefreshToken;
import com.grapefruitade.honeypost.domain.auth.exception.*;
import com.grapefruitade.honeypost.domain.auth.repository.RefreshTokenRepository;
import com.grapefruitade.honeypost.domain.user.presentation.dto.res.UserInfoRes;
import com.grapefruitade.honeypost.domain.user.entity.User;
import com.grapefruitade.honeypost.domain.user.repository.UserRepository;
import com.grapefruitade.honeypost.global.security.exception.TokenExpirationException;
import com.grapefruitade.honeypost.domain.auth.exception.TokenNotFoundException;
import com.grapefruitade.honeypost.global.security.exception.TokenNotValidException;
import com.grapefruitade.honeypost.global.security.jwt.TokenProvider;
import com.grapefruitade.honeypost.global.util.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserUtil userUtil;

    public UserInfoRes register(RegisterReq registerRequest){
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new ExistUsernameException();
        }

        User user = registerRequest.user(passwordEncoder);
        return UserInfoRes.userResponseDto(userRepository.save(user));
    }

    public TokenRes login(LoginReq loginRequest){

      User user = userRepository.findByUsername(loginRequest.getUsername())
              .orElseThrow(UserNotFoundException::new);

      if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
          throw new MismatchNotPassword();
      }

      TokenRes tokenDto = tokenProvider.generateTokenDto(user.getId(), user.getRole());

      saveRefreshToken(tokenDto, user);

        return tokenDto;
    }

    private void saveRefreshToken(TokenRes tokenDto, User user) {
        RefreshToken token = RefreshToken.builder()
                .token(tokenDto.getRefreshToken())
                .userId(user.getId())
                .expiresAt(tokenDto.getRefreshTokenExpiresIn())
                .build();

        refreshTokenRepository.save(token);
    }

    public TokenRes refresh (String refreshToken){
        String parseRefreshToken = tokenProvider.parseRefreshToken(refreshToken);


        RefreshToken validRefreshToken = refreshTokenRepository.findById(parseRefreshToken)
                .orElseThrow(TokenExpirationException::new);

        User user = userRepository.findById(validRefreshToken.getUserId())
                .orElseThrow(UserNotFoundException::new);


        TokenRes tokenDto = tokenProvider.generateTokenDto(user.getId(), user.getRole());

        saveRefreshToken(tokenDto, user);
        refreshTokenRepository.deleteById(validRefreshToken.getToken());

        return tokenDto;
    }


    public void logout(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);

        User user = userUtil.currentUser();

        RefreshToken validToken = refreshTokenRepository.findByUserId(user.getId())
                .orElseThrow(ExpiredRefreshTokenException::new);

        refreshTokenRepository.delete(validToken);

    }


}
