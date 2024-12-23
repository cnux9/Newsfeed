package com.sparta.newsfeed.auth.controller;

import com.sparta.newsfeed.auth.dto.AuthRequestDto;
import com.sparta.newsfeed.auth.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public void login(@RequestBody AuthRequestDto dto, HttpSession session) {
        authService.login(dto, session);
    }

    // TODO: 컨틀로러에서 처리? 서비스에서 처리?
    @GetMapping("/logout")
    public void login(HttpSession session) {
        authService.logout(session);
    }
}
