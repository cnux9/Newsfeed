package com.sparta.newsfeed.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

public record CommentRequestDto(
        Long newsfeedId,
        String contents
) {

}