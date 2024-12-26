package com.sparta.newsfeed.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDeleteRequestDto {
    @NotNull
    private final String password;
}
