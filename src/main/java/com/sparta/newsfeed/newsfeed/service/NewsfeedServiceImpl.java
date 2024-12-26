package com.sparta.newsfeed.newsfeed.service;

import com.sparta.newsfeed.Page;
import com.sparta.newsfeed.PageQuery;
import com.sparta.newsfeed.auth.service.AuthService;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.exception.CustomException;
import com.sparta.newsfeed.friend.entity.FriendRequest;
import com.sparta.newsfeed.friend.repository.FriendRequestRepository;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedRequestDto;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedRequestQueryDto;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedResponseDto;
import com.sparta.newsfeed.newsfeed.entity.Newsfeed;
import com.sparta.newsfeed.newsfeed.repository.NewsfeedRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsfeedServiceImpl implements NewsfeedService {
    private final NewsfeedRepository newsfeedRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final AuthService authService;
    private final HttpSession session;
    private final EntityManager entityManager;

    @Override
    public NewsfeedResponseDto createNewsfeed(NewsfeedRequestDto requestDto) {
        User user = getAuthenticatedUser();

        Newsfeed newsfeed = new Newsfeed(user, requestDto.getTitle(), requestDto.getContent());
        Newsfeed savedNewsfeed = newsfeedRepository.save(newsfeed);

        return NewsfeedResponseDto.toDto(savedNewsfeed);
    }

    @Override
    public Page<NewsfeedResponseDto> findNewsfeed(
            PageQuery page,
            NewsfeedRequestQueryDto dto
    ) {
        User user = getAuthenticatedUser();

        List<Long> friendsIds = new ArrayList<>(friendRequestRepository.findByUser(user.getId())
                .stream()
                .map(FriendRequest::getId)
                .toList());

        friendsIds.add(user.getId());

        return Page.from(
                newsfeedRepository.findAll(
                        page.toPageable(),
                                dto,
                                friendsIds
                        )
                .map(NewsfeedResponseDto::toDto));
    }

    @Override
    public NewsfeedResponseDto updateNewsfeed(Long id, NewsfeedRequestDto dto) {
        User user = getAuthenticatedUser();
        Newsfeed targetNewsfeed = newsfeedRepository.findById(id);

        if (!user.getId().equals(targetNewsfeed.getId())) {
            throw new CustomException.UnauthorizedException("자신의 피드만 수정할 수 있습니다.");
        }

        targetNewsfeed = newsfeedRepository.save(targetNewsfeed.partialUpdate(dto));
        return NewsfeedResponseDto.toDto(targetNewsfeed);
    }

    @Override
    public boolean deleteNewsfeed(Long id) {
        User user = getAuthenticatedUser();
        Newsfeed feed = newsfeedRepository.findById(id);
        if(!user.getId().equals(feed.getUser().getId()))
            throw new CustomException.UnauthorizedException("자신의 피드만 삭제할 수 있습니다.");
        return newsfeedRepository.delete(id);
    }

    private User getAuthenticatedUser() {
        User user = userRepository.findUserByEmail(authService.getSessionEmail(session)).orElse(null);
        if (user == null) {
            throw new CustomException.UnauthorizedException("유효한 세션이 아닙니다!");
        }
        return user;
    }
}