package com.sparta.newsfeed.friend.service;

import com.sparta.newsfeed.exception.CustomException;
import com.sparta.newsfeed.friend.dto.FriendRequestDto;
import com.sparta.newsfeed.friend.dto.FriendResponseDto;
import com.sparta.newsfeed.friend.entity.Friend;
import com.sparta.newsfeed.friend.entity.request_state;
import com.sparta.newsfeed.friend.repository.FriendRepository;
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
    private final HttpSession httpSession;

    @Override
    public FriendResponseDto addFriend(FriendRequestDto friendRequestDto) {
        Friend friend = new Friend(friendRequestDto.getRequested(), friendRequestDto.getReceived(), request_state.REQUESTED);
        Friend savedFriend = friendRepository.save(friend);

        return new FriendResponseDto(savedFriend.getReceived(), savedFriend.getRequested(), savedFriend.getState());
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

    /**
     * 내 ID를 넣어야한다.
     * 세션에서 불러온다.
     * @return
     */
    @Override
    public List<FriendResponseDto> getAllFriends() {
        UUID uuid = (UUID) httpSession.getAttribute("sessionKey");
        friendRepository.findById
        List<Friend> friends = friendRepository.findByState(request_state.ACCEPTED);
        return friends.stream()
                .map(FriendResponseDto::new)
                .collect(Collectors.toList());
    }
}