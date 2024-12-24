package com.sparta.newsfeed.friend.service;

import com.sparta.newsfeed.friend.dto.FriendRequestDto;
import com.sparta.newsfeed.friend.dto.FriendResponseDto;

import java.util.List;

public interface FriendService {
    FriendResponseDto addFriend(FriendRequestDto friendRequestDto);

    FriendResponseDto replyFriend(Long id, Boolean isAccepted);

    List<Long> getAllFriends();

    List<FriendResponseDto> getRequestFriends();

    void deleteFriend(FriendRequestDto friendRequestDto);
}
