package com.grapefruitade.honeypost.domain.user.controller;

import com.grapefruitade.honeypost.domain.user.dto.res.UserPostInfoListRes;
import com.grapefruitade.honeypost.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserPostInfoListRes> getMyPost(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getMyPosts());
    }
}
