package com.sparta.newsfeed.friend.dto;

import lombok.Getter;

@Getter
public class FriendRequestDto {
    Long requested;
    Long received;
    int state = 0;
}
