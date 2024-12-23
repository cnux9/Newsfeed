package com.sparta.newsfeed.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {
    private final String name;
    private final String email;
    private final String password;
}
