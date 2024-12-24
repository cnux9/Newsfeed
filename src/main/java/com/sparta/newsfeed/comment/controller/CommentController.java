package com.sparta.newsfeed.comment.controller;


import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import com.sparta.newsfeed.comment.dto.CommentResponseDto;
import com.sparta.newsfeed.comment.service.CommentService;
import com.sparta.newsfeed.user.dto.SignUpRequestDto;
import com.sparta.newsfeed.user.dto.SignUpResponseDto;
import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestBody CommentRequestDto requestDto,
            HttpSession session
    ) {
        CommentResponseDto responseDto = commentService.createComment(requestDto, session);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<List<CommentResponseDto>> findComment(@PathVariable Long taskId) {
        List<CommentResponseDto> responseDtoList = commentService.findComments(taskId);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto requestDto,
            HttpSession session
    ) {
        UserResponseDto responseDto =
                commentService.updateComment(
                        id,
                        requestDto,
                        session
                );
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, HttpSession session) {
        commentService.deleteComment(id, session);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
