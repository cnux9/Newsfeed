package com.sparta.newsfeed.newsfeed.service;

import com.sparta.newsfeed.auth.service.AuthService;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedRequestDto;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedResponseDto;
import com.sparta.newsfeed.newsfeed.entity.Newsfeed;
import com.sparta.newsfeed.newsfeed.repository.NewsfeedRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsfeedServiceImpl implements NewsfeedService{
    private final UserRepository userRepository;
    private final NewsfeedRepository repository;
    private final HttpServletRequest httpServletRequest;
    private final AuthService authService;

    @Override
    public NewsfeedResponseDto createNewsfeed(NewsfeedRequestDto requestDto, HttpSession session) {
        String sessionEmail= authService.getSessionEmail(session);
        User foundUser = userRepository.findUserByEmail(sessionEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Newsfeed newsfeed = new Newsfeed(foundUser, requestDto.getTitle(), requestDto.getContent());
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