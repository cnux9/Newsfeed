package com.sparta.newsfeed.newsfeed.service;

import com.sparta.newsfeed.newsfeed.dto.NewsfeedRequestDto;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedResponseDto;

import java.util.List;

public interface NewsfeedService {
    //Create
    NewsfeedResponseDto createNewsfeed(NewsfeedRequestDto newsfeedRequestDto);
    //Read
    List<NewsfeedResponseDto> getNewsfeed();
    //Update
    NewsfeedResponseDto updateNewsfeed(NewsfeedRequestDto newsfeedRequestDto);
    //Delete
    boolean deleteNewsfeed(Long id);
}
