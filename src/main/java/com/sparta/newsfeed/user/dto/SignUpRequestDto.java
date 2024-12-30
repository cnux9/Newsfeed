package com.sparta.newsfeed.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {
    private final String name;
    @Email
    private final String email;
    // TODO: 패턴 상수화 필요
    private final String password;
}