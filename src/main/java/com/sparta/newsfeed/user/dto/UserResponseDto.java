package com.sparta.newsfeed.user.dto;

import com.sparta.newsfeed.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private final Long id;
    private final String email;
    private final String name;

    public static UserResponseDto toDto(User user){
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }
}