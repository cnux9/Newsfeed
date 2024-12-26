package com.sparta.newsfeed.comment.dto;

import com.sparta.newsfeed.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private final String name;
    private final String contents;
    private final LocalDateTime createAt;


    public CommentResponseDto(Comment comment) {
        this.name = comment.getUser().getName();
        this.contents = comment.getContents();
        this.createAt = comment.getCreatedAt();
    }

    public static CommentResponseDto toDto(Comment comment){
        return new CommentResponseDto(
                comment.getUser().getName(),
                comment.getContents(),
                comment.getCreatedAt()
        );
    }
}
