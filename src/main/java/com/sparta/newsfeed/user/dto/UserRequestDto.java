package com.sparta.newsfeed.user.dto;

public class UserRequestDto {
    private final String email;
    private final String name;

    public UserRequestDto(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
