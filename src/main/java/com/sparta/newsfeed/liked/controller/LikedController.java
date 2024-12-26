package com.sparta.newsfeed.liked.controller;

import com.sparta.newsfeed.liked.dto.LikedResponseDto;
import com.sparta.newsfeed.liked.service.LikedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/liked")
public class LikedController {
    private final LikedService likedService;

    @PutMapping("/newsfeed/{id}")
    public ResponseEntity<LikedResponseDto> likedNewsfeed(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(likedService.UpdateForNewsfeedLiked(id), HttpStatus.OK);
    }

    /*
    @PutMapping("/comment/{id}")
    public ResponseEntity<Integer> likedComment(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(likedService.UpdateForCommentLiked(id), HttpStatus.OK);
    }
     */
}