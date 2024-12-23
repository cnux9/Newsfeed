package com.sparta.newsfeed.newsfeed.service;

import com.sparta.newsfeed.PageQuery;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedRequestDto;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NewsfeedService {
    //Create
    NewsfeedResponseDto createNewsfeed(NewsfeedRequestDto newsfeedRequestDto);
    //Read
    Page<NewsfeedResponseDto> findNewsfeed(PageQuery page);
    //Update
    NewsfeedResponseDto updateNewsfeed(NewsfeedRequestDto newsfeedRequestDto);
    //Delete
    boolean deleteNewsfeed(Long id);
}
