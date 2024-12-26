package com.sparta.newsfeed.comment.dto;

public record CommentRequestDto(
        Long newsfeedId,
        String contents
) { }