package com.sparta.newsfeed.newsfeed.service;

import com.sparta.newsfeed.newsfeed.dto.NewsfeedRequestDto;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedResponseDto;
import com.sparta.newsfeed.newsfeed.repository.NewsfeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsfeedServiceImpl implements NewsfeedService{
    private final NewsfeedRepository repository;


    @Override
    public NewsfeedResponseDto createNewsfeed(NewsfeedRequestDto newsfeedRequestDto) {
        return null;
    }

    @Override
    public List<NewsfeedResponseDto> getNewsfeed() {

        return null;
    }

    @Override
    public NewsfeedResponseDto updateNewsfeed(NewsfeedRequestDto newsfeedRequestDto) {
        return null;
    }

    @Override
    public void deleteNewsfeed(Long id) {
        repository.deleteById(id);
    }
}