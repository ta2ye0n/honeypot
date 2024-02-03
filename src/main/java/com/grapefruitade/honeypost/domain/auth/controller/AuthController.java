package com.grapefruitade.honeypost.domain.auth.controller;

import com.grapefruitade.honeypost.domain.auth.dto.LoginRequestDto;
import com.grapefruitade.honeypost.domain.auth.dto.RegisterRequestDto;
import com.grapefruitade.honeypost.domain.auth.dto.TokenDto;
import com.grapefruitade.honeypost.domain.auth.dto.TokenRequestDto;
import com.grapefruitade.honeypost.domain.auth.service.AuthService;
import com.grapefruitade.honeypost.domain.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<UserResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto){
        return ResponseEntity.ok(authService.register(registerRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestBody TokenRequestDto tokenRequestDto){
        return ResponseEntity.ok(authService.refresh(tokenRequestDto));
    }


}
