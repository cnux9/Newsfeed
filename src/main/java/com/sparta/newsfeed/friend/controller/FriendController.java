package com.sparta.newsfeed.friend.controller;

import com.sparta.newsfeed.friend.dto.FriendRequestDto;
import com.sparta.newsfeed.friend.dto.FriendResponseDto;
import com.sparta.newsfeed.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/friend")
public class FriendController {

    private final FriendService friendService;

    //친구 요청
    @PostMapping("/request")
    public ResponseEntity<FriendResponseDto> addFriend(@RequestBody FriendRequestDto friendRequestDto) {
        return new ResponseEntity<>(friendService.addFriend(friendRequestDto), HttpStatus.OK);
    }

    //친구 수락/거절
    @PostMapping("/reply")
    public FriendResponseDto replyFriend(@RequestBody FriendRequestDto friendRequestDto) {
        return friendService.replyFriend(friendRequestDto);
    }

    //친구 요청 불러오기
    @GetMapping("/request")
    public List<FriendResponseDto> getRequestFriends() {
        return friendService.getRequestFriends();
    }

    //친구 진짜 불러오기
    @GetMapping("/friend")
    public List<FriendResponseDto> getAllFriends() {
        return friendService.getAllFriends();
    }

    //친구 삭제
    @DeleteMapping
    public void deleteFriend(@RequestBody FriendRequestDto friendRequestDto) {
        friendService.deleteFriend(friendRequestDto);
    }
}
