package com.sparta.newsfeed.newsfeed.controller;

import com.sparta.newsfeed.newsfeed.dto.NewsfeedRequestDto;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedResponseDto;
import com.sparta.newsfeed.newsfeed.service.NewsfeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/newsfeed")
public class NewsfeedController {
    private final NewsfeedService newsfeedService;

    @PostMapping
    public ResponseEntity<NewsfeedResponseDto> createNewsfeed(
            @RequestBody NewsfeedRequestDto newsfeedRequestDto
    ) {
        return new ResponseEntity<>(newsfeedService.createNewsfeed(newsfeedRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NewsfeedResponseDto>> getNewsfeed() {
        return new ResponseEntity<>(newsfeedService.getNewsfeed(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<NewsfeedResponseDto> updateNewsfeed(
            @RequestBody NewsfeedRequestDto newsfeedRequestDto
            ) {
        return new ResponseEntity<>(newsfeedService.updateNewsfeed(newsfeedRequestDto), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteNewsfeed(
            @PathVariable Long id
    ) {
        newsfeedService.deleteNewsfeed(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
