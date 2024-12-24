package com.sparta.newsfeed.friend.dto;

import com.sparta.newsfeed.friend.entity.FriendRequest;
import com.sparta.newsfeed.friend.entity.request_state;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendResponseDto {
    Long requested;
    Long received;
    request_state state;

    public FriendResponseDto(FriendRequest friend) {
        this.requested = friend.getRequested();
        this.received = friend.getReceived();
        this.state = friend.getState();
    }
}
