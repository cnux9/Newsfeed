package com.sparta.newsfeed.newsfeed.service;

import com.sparta.newsfeed.PageQuery;
import com.sparta.newsfeed.auth.service.AuthService;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedRequestDto;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedResponseDto;
import com.sparta.newsfeed.newsfeed.entity.Newsfeed;
import com.sparta.newsfeed.newsfeed.repository.NewsfeedRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsfeedServiceImpl implements NewsfeedService{
    private final NewsfeedRepository newsfeedRepository;
    private final UserRepository userRepository;
    //private final FriendRequestRepository friendRequestRepository;
    private final AuthService authService;
    private final HttpSession session;

    @Override
    public NewsfeedResponseDto createNewsfeed(NewsfeedRequestDto requestDto) {
        Newsfeed newsfeed = new Newsfeed(requestDto.getTitle(), requestDto.getContent());
        Newsfeed savedNewsfeed = newsfeedRepository.save(newsfeed);

        return new NewsfeedResponseDto(savedNewsfeed.getTitle(), savedNewsfeed.getContents());
    }

    @Override
    public Page<NewsfeedResponseDto> findNewsfeed(PageQuery page) {
        User user = userRepository.findUserByEmailOrElseThrow(authService.getUserEmail(session));

        List<Long> friendsIds = new ArrayList<>();//friendRequestRepository.findAllFriendsId(user.getId());
        //friendsIds.add(user.getId())

        newsfeedRepository.findAll(page.toPageable(), friendsIds);
        return null;

        /*
        Long userId = (Long) httpServletRequest.getSession().getId();
        repository.findAll(userId);

        return null;

         */
    }

    @Override
    public NewsfeedResponseDto updateNewsfeed(NewsfeedRequestDto newsfeedRequestDto) {
        //TODO: 세션값이 아직 없어요..
        return null;
    }

    @Override
    public boolean deleteNewsfeed(Long id) {
        return newsfeedRepository.delete(id);
    }
}