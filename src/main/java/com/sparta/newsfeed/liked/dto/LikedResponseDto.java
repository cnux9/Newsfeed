package com.sparta.newsfeed.liked.dto;

import lombok.Getter;

@Getter
public record LikedResponseDto(
        Long id,
        int likedCount
){}
