package com.sparta.newsfeed.newsfeed.dto;

import com.sparta.newsfeed.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewsfeedResponseDto extends BaseEntity {
    private String title;
    private String content;
}
