package com.sparta.newsfeed.newsfeed.service;

import com.sparta.newsfeed.newsfeed.dto.NewsfeedRequestDto;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedResponseDto;
import com.sparta.newsfeed.newsfeed.entity.Newsfeed;
import com.sparta.newsfeed.newsfeed.repository.NewsfeedRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsfeedServiceImpl implements NewsfeedService{
    private final NewsfeedRepository repository;
    private final HttpServletRequest httpServletRequest;

    @Override
    public NewsfeedResponseDto createNewsfeed(NewsfeedRequestDto requestDto) {
        Newsfeed newsfeed = new Newsfeed(requestDto.getTitle(), requestDto.getContent());
        Newsfeed savedNewsfeed = repository.save(newsfeed);

        return new NewsfeedResponseDto(savedNewsfeed.getTitle(), savedNewsfeed.getContents());
    }

    //TODO :: 세션값 필요
    @Override
    public List<NewsfeedResponseDto> getNewsfeed() {
        /*
        Long userId = (Long) httpServletRequest.getSession().getId();
        repository.findAll(userId);


         */
        return null;
    }

    @Override
    public NewsfeedResponseDto updateNewsfeed(NewsfeedRequestDto newsfeedRequestDto) {
        //TODO: 세션값이 아직 없어요..
        return null;
    }

    @Override
    public boolean deleteNewsfeed(Long id) {
        return repository.delete(id);
    }
}