package com.sparta.newsfeed.auth.controller;

import com.sparta.newsfeed.auth.dto.AuthRequestDto;
import com.sparta.newsfeed.auth.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public void login(@RequestBody AuthRequestDto authRequestDto, HttpSession session) {
        authService.login(authRequestDto, session);
    }
}
