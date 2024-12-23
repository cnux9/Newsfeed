package com.sparta.newsfeed.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponseDto {
    private final String name;
    private final String email;
}
