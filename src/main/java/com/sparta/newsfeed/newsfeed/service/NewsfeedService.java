package com.sparta.newsfeed.newsfeed.service;

import com.sparta.newsfeed.Page;
import com.sparta.newsfeed.PageQuery;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedRequestDto;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedRequestQueryDto;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedResponseDto;

import java.time.LocalDateTime;

public interface NewsfeedService {
    //Create
    NewsfeedResponseDto createNewsfeed(NewsfeedRequestDto newsfeedRequestDto);
    //Read
    Page<NewsfeedResponseDto> findNewsfeed(
            PageQuery page,
            NewsfeedRequestQueryDto dto
    );
    //Update
    NewsfeedResponseDto updateNewsfeed(Long id, NewsfeedRequestDto newsfeedRequestDto);
    //Delete
    boolean deleteNewsfeed(Long id);
}
