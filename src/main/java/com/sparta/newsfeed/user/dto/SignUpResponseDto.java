package com.sparta.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class SignUpResponseDto {
    private final String name;
    private final String email;

    public SignUpResponseDto(String name,String email) {
        this.name = name;
        this.email = email;
    }
}
