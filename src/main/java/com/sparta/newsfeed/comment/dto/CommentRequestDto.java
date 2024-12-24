package com.sparta.newsfeed.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequestDto {
    @JsonProperty("newsfeed_id")
    private final Long newsfeedId;
    private final String contents;
}
