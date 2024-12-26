package com.sparta.newsfeed.comment.controller;


import com.sparta.newsfeed.Page;
import com.sparta.newsfeed.PageQuery;
import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import com.sparta.newsfeed.comment.dto.CommentResponseDto;
import com.sparta.newsfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newsfeed/{newsfeedId}/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestBody CommentRequestDto requestDto
    ) {
        CommentResponseDto responseDto = commentService.createComment(requestDto);
        return new ResponseEntity<>(
                responseDto,
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<CommentResponseDto>> findAllComment(
            PageQuery pageQuery,
            @PathVariable Long newsfeedId
    ) {
        return new ResponseEntity<>(
                commentService.findAllComments(pageQuery, newsfeedId),
                HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto
    ) {
        CommentResponseDto responseDto =
                commentService.updateComment(
                        commentId,
                        requestDto
                );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
