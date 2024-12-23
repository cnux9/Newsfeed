package com.sparta.newsfeed.auth.controller;

import com.sparta.newsfeed.auth.dto.AuthRequestDto;
import com.sparta.newsfeed.auth.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequestDto dto, HttpSession session) {
        authService.login(dto, session);
        return new ResponseEntity<>("login success.", HttpStatus.OK);
    }

    // TODO: 컨틀로러에서 처리? 서비스에서 처리?
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        authService.logout(session);
        return new ResponseEntity<>("logout success.", HttpStatus.OK);
    }
}
