package com.sparta.newsfeed.user.dto;

import com.sparta.newsfeed.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private final String email;
    private final String name;

    public UserResponseDto(User foundUser) {
        this.email = foundUser.getEmail();
        this.name = foundUser.getName();
    }
}