package com.sparta.newsfeed.newsfeed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NewsfeedRequestQueryDto {
    private LocalDateTime start = null;
    private LocalDateTime end = null;
}
