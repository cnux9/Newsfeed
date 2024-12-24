package com.sparta.newsfeed.newsfeed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewsfeedRequestDto {
    private String title;
    private String content;
}