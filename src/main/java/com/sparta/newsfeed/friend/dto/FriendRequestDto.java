package com.sparta.newsfeed.friend.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class FriendRequestDto {
    Long requested;
    Long received;
    @Setter
    int state = 0;
}
