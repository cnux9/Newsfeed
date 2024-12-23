package com.sparta.newsfeed.friend.service;

import com.sparta.newsfeed.auth.service.AuthService;
import com.sparta.newsfeed.exception.CustomException;
import com.sparta.newsfeed.friend.dto.FriendRequestDto;
import com.sparta.newsfeed.friend.dto.FriendResponseDto;
import com.sparta.newsfeed.friend.entity.Friend;
import com.sparta.newsfeed.friend.entity.request_state;
import com.sparta.newsfeed.friend.repository.FriendRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final AuthService authService;

    @Override
    public FriendResponseDto addFriend(FriendRequestDto friendRequestDto) {
        Friend friend = new Friend(friendRequestDto.getRequested(), friendRequestDto.getReceived(), request_state.REQUESTED);
        Friend savedFriend = friendRepository.save(friend);

        return new FriendResponseDto(savedFriend.getRequested(), savedFriend.getReceived(), savedFriend.getState());
    }

    @Override
    public FriendResponseDto replyFriend(FriendRequestDto friendRequestDto) {
        request_state state = request_state.of(friendRequestDto.getState());

        if (state == request_state.REJECTED) {
            //친구를 불러와서 save
            return new FriendResponseDto(friendRequestDto.getRequested(), friendRequestDto.getReceived(), request_state.REJECTED);
        } else if (state == request_state.ACCEPTED) {
            return new FriendResponseDto(friendRequestDto.getRequested(), friendRequestDto.getReceived(), request_state.ACCEPTED);
        } else {
            throw new CustomException.BadRequestException("Invalid request state");
        }
    }

    @Override
    public List<FriendResponseDto> getAllFriends() {
        UUID uuid = (UUID) httpSession.getAttribute("sessionKey");
        String userEmail = authService.getUserEmail(uuid);
        User user = userRepository.findUserByEmailOrElseThrow(userEmail);

        List<Friend> friends = friendRepository.findByUser(user.getId());
        return friends.stream()
                .map(FriendResponseDto::new)
                .collect(Collectors.toList());
    }
}