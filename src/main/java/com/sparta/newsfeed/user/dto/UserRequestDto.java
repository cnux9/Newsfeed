package com.sparta.newsfeed.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDto {
    private final String email;
    private final String name;
    @NotNull
    @JsonProperty("old_password")
    private final String oldPassword;
    @NotNull
    @JsonProperty("new_password")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$")
    // TODO: 패턴 상수화 필요
    private final String newPassword;
}
