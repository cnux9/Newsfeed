package com.sparta.newsfeed.friend.service;

import com.sparta.newsfeed.auth.service.AuthService;
import com.sparta.newsfeed.exception.CustomException;
import com.sparta.newsfeed.friend.dto.FriendRequestDto;
import com.sparta.newsfeed.friend.dto.FriendResponseDto;
import com.sparta.newsfeed.friend.entity.FriendRequest;
import com.sparta.newsfeed.friend.entity.request_state;
import com.sparta.newsfeed.friend.repository.FriendRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final AuthService authService;

    @Override
    public FriendResponseDto addFriend(FriendRequestDto friendRequestDto) {
        //이메일로부터 유저 찾기
        User friend = userRepository.findUserByEmail(friendRequestDto.getEmail()).get();
        //유저로부터 아이디 가져오기
        Long friendId = friend.getId();

        //세션에서 이메일 가져오기
        String userEmail = authService.getSessionEmail(httpSession);
        //이메일로부터 유저 찾기
        User user = userRepository.findUserByEmail(userEmail).get();
        //유저로부터 아이디 가져오기
        Long myId = user.getId();

        //두 아이디 넣어서 FriendRequest Entity 만들기
        FriendRequest friendRequest = new FriendRequest(myId, friendId, request_state.REQUESTED);
        //Repository에 저장하기
        friendRepository.save(friendRequest);
        //return "성공했습니다"

        return new FriendResponseDto(friendRequest);
    }

    @Override
    public FriendResponseDto replyFriend(FriendRequestDto friendRequestDto) {
        //혁규님 이메일 ID로 변환
        User friend = userRepository.findUserByEmail(friendRequestDto.getEmail()).get();
        Long friendId = friend.getId();

        //내 UUID 및 이메일 세션에서 가져오기
        String userEmail = authService.getSessionEmail(httpSession);
        //이메일로 아이디 가져오기
        User user = userRepository.findUserByEmail(userEmail).get();
        Long userId = user.getId();

        //state 추출하기
        request_state state = request_state.of(friendRequestDto.getState());

        //state 확인 및 상태 수정
        if (state == request_state.REJECTED) {
            FriendRequest friendRequest = friendRepository.findByReceived(friendId, userId);
            friendRequest.setState(request_state.REJECTED);
            friendRepository.save(friendRequest);

            return new FriendResponseDto(friendRequest.getRequested(), friendRequest.getReceived(), friendRequest.getState());
        } else if (state == request_state.ACCEPTED) {
            FriendRequest friendRequest = friendRepository.findByReceived(friendId, userId);
            friendRequest.setState(request_state.ACCEPTED);
            friendRepository.save(friendRequest);

            return new FriendResponseDto(friendRequest.getRequested(), friendRequest.getReceived(), friendRequest.getState());
        } else {
            throw new CustomException.BadRequestException("Invalid request state");
        }
    }

    @Override
    public List<FriendResponseDto> getRequestFriends() {
        String userEmail = authService.getSessionEmail(httpSession);
        User user = userRepository.findUserByEmail(userEmail).get();
        Long userId = user.getId();

        List<FriendRequest> friends = friendRepository.findByUser(userId);
        return friends.stream()
                .filter(friendRequest -> isValidRequest(request_state.REJECTED, friendRequest, userId))
                .map(FriendResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<FriendResponseDto> getAllFriends() {
        String userEmail = authService.getSessionEmail(httpSession);
        User user = userRepository.findUserByEmail(userEmail).get();
        Long userId = user.getId();

        List<FriendRequest> friends = friendRepository.findByUser(userId);
        return friends.stream()
                .filter(friendRequest -> isValidRequest(request_state.ACCEPTED, friendRequest, userId))
                .map(FriendResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFriend(FriendRequestDto friendRequestDto) {
        User friend = userRepository.findUserByEmail(friendRequestDto.getEmail()).get();
        Long friendId = friend.getId();

        String userEmail = authService.getSessionEmail(httpSession);
        User user = userRepository.findUserByEmail(userEmail).get();
        Long userId = user.getId();

        FriendRequest received = friendRepository.findByReceived(friendId, userId);
        FriendRequest request = friendRepository.findByReceived(userId, friendId);

        if (received == null && request == null) {
            throw new CustomException.BadRequestException("Invalid request state");
        }
        if (received != null) {
            friendRepository.deleteById(received.getId());
        }
        if (request != null) {
            friendRepository.deleteById(request.getId());
        }
    }

    private boolean isValidRequest(request_state state, FriendRequest friendRequest, Long userId) {
        if (friendRequest.getState() == state && friendRequest.getRequested().equals(userId) && friendRequest.getReceived().equals(userId)) {
            return true;
        }
        return false;
    }
}