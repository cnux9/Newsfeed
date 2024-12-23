package com.sparta.newsfeed.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDto {
    private final String email;
    private final String name;
}
