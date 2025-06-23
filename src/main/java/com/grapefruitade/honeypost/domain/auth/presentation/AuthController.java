package com.grapefruitade.honeypost.domain.auth.presentation;

import com.grapefruitade.honeypost.domain.auth.presentation.dto.req.LoginReq;
import com.grapefruitade.honeypost.domain.auth.presentation.dto.req.RegisterReq;
import com.grapefruitade.honeypost.domain.auth.presentation.dto.res.TokenRes;
import com.grapefruitade.honeypost.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<String> register(@RequestBody @Valid RegisterReq registerRequestDto){
        authService.register(registerRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenRes> login(@RequestBody @Valid LoginReq loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PatchMapping
    public ResponseEntity<TokenRes> refresh(@RequestHeader String refreshToken){
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

    @DeleteMapping
    public ResponseEntity<String> logout(HttpServletRequest request){
        authService.logout(request);
        return ResponseEntity.noContent().build();
    }

}
