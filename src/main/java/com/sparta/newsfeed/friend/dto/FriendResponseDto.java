package com.sparta.newsfeed.friend.dto;

import com.sparta.newsfeed.friend.entity.Friend;
import com.sparta.newsfeed.friend.entity.request_state;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FriendResponseDto {
    Long requested;
    Long received;
    request_state state;

    public FriendResponseDto(Friend friend) {
        this.requested = friend.getRequested();
        this.received = friend.getReceived();
        this.state = friend.getState();
    }
}
