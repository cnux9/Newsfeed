package com.sparta.newsfeed.newsfeed.dto;

import com.sparta.newsfeed.BaseEntity;
import lombok.Getter;

@Getter
public class NewsfeedRequestDto extends BaseEntity {
    private String title;
    private String content;
}