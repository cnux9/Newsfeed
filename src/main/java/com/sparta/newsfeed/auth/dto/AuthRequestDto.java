package com.sparta.newsfeed.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthRequestDto {
    @NotNull
    private String email;
    @NotNull
    private String password;


}
