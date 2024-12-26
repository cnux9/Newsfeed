package com.sparta.newsfeed.newsfeed.dto;

import com.sparta.newsfeed.newsfeed.entity.Newsfeed;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class NewsfeedResponseDto {
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likedCount;

    public static NewsfeedResponseDto toDto(Newsfeed entity) {
        return new NewsfeedResponseDto(
                entity.getTitle(),
                entity.getContents(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getLikedCount()
        );
    }
}