package com.sparta.newsfeed.user.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {
    private final String name;
    @Email
    private final String email;
    private final String password;
}
