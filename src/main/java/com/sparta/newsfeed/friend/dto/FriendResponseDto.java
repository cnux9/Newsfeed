package com.sparta.newsfeed.friend.dto;

import com.sparta.newsfeed.friend.entity.FriendRequest;
import com.sparta.newsfeed.friend.entity.request_state;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendResponseDto {
    Long id;
    Long requested;
    Long received;
    request_state state;

    public static FriendResponseDto toDto(FriendRequest friend) {
        return new FriendResponseDto(
                friend.getId(),
                friend.getRequested(),
                friend.getReceived(),
                friend.getState()
        );
    }
}
