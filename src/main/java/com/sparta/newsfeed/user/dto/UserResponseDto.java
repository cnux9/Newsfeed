package com.sparta.newsfeed.user.dto;

public class UserResponseDto {
    private final String id;
    private final String email;
    private final String name;

    public UserResponseDto(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
