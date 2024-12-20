package com.sparta.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class SignUpRequestDto {
    private final String name;
    private final String email;
    private final String password;

    public SignUpRequestDto(String name,String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
