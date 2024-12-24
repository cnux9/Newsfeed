package com.sparta.newsfeed.friend.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class FriendRequestDto {
    String email;
    @Setter
    int state = 0;
}
