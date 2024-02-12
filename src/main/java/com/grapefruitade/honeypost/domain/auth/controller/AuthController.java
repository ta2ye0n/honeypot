package com.grapefruitade.honeypost.domain.auth.controller;

import com.grapefruitade.honeypost.domain.auth.dto.LoginRequestDto;
import com.grapefruitade.honeypost.domain.auth.dto.RegisterRequestDto;
import com.grapefruitade.honeypost.domain.auth.dto.TokenDto;
import com.grapefruitade.honeypost.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDto registerRequestDto){
        authService.register(registerRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PatchMapping
    public ResponseEntity<TokenDto> refresh(@RequestHeader String refreshToken){
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

    @DeleteMapping
    public ResponseEntity<String> logout(@RequestHeader String refreshToken){
        authService.logout(refreshToken);
        return ResponseEntity.ok().body("로그아웃이 완료되었습니다.");
    }

}
