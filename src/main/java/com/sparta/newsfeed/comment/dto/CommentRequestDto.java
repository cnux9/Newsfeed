package com.sparta.newsfeed.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequestDto {
    private final Long newsfeedId;
    private final String contents;
}
