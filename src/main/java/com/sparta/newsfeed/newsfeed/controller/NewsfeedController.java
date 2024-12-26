package com.sparta.newsfeed.newsfeed.controller;

import com.sparta.newsfeed.Page;
import com.sparta.newsfeed.PageQuery;
import com.sparta.newsfeed.liked.service.LikedService;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedRequestDto;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedRequestQueryDto;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedResponseDto;
import com.sparta.newsfeed.newsfeed.service.NewsfeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/newsfeed")
public class NewsfeedController {
    private final NewsfeedService newsfeedService;
    private final LikedService likedService;

    @PostMapping
    public ResponseEntity<NewsfeedResponseDto> createNewsfeed(
            @RequestBody NewsfeedRequestDto newsfeedRequestDto
    ) {
        return new ResponseEntity<>(newsfeedService.createNewsfeed(newsfeedRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<NewsfeedResponseDto>> findNewsfeed(
            PageQuery page,
            @ModelAttribute NewsfeedRequestQueryDto dto
            ) {
        return new ResponseEntity<>(newsfeedService.findNewsfeed(page, dto), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<NewsfeedResponseDto> updateNewsfeed(
            @PathVariable Long id,
            @RequestBody NewsfeedRequestDto newsfeedRequestDto
            ) {
        return new ResponseEntity<>(newsfeedService.updateNewsfeed(id, newsfeedRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteNewsfeed(
            @PathVariable Long id
    ) {
        newsfeedService.deleteNewsfeed(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
