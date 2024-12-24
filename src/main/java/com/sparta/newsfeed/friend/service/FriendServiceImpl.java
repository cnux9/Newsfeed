package com.sparta.newsfeed.friend.service;

import com.sparta.newsfeed.auth.service.AuthService;
import com.sparta.newsfeed.exception.CustomException;
import com.sparta.newsfeed.friend.dto.FriendRequestDto;
import com.sparta.newsfeed.friend.dto.FriendResponseDto;
import com.sparta.newsfeed.friend.entity.FriendRequest;
import com.sparta.newsfeed.friend.entity.request_state;
import com.sparta.newsfeed.friend.repository.FriendRequestRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final AuthService authService;

    @Override
    public FriendResponseDto addFriend(FriendRequestDto friendRequestDto) {
        //세션에서 이메일 가져오기
        String userEmail = authService.getSessionEmail(httpSession);
        if(friendRequestDto.getEmail().equals(userEmail))
            throw new CustomException.BadRequestException("자기 자신은 친구로 추가할 수 없습니다.");
        //이메일로부터 유저 찾기
        User user = userRepository.findUserByEmail(userEmail).orElseThrow();
        //유저로부터 아이디 가져오기
        Long myId = user.getId();

        //이메일로부터 유저 찾기
        User friend = userRepository.findUserByEmail(friendRequestDto.getEmail()).orElse(null);
        if(friend == null)
            throw new NoSuchElementException("해당 이메일을 가진 유저: " + friendRequestDto.getEmail() + " 는 존재하지 않습니다.");
        //유저로부터 아이디 가져오기
        Long friendId = friend.getId();


        //두 아이디 넣어서 FriendRequest Entity 만들기
        FriendRequest friendRequest = new FriendRequest(myId, friendId, request_state.REQUESTED);
        if (isDuplicated(friendRequest))
            throw new CustomException.BadRequestException("이미 친구 추가 요청이 되어 있습니다.");

        //Repository 에 저장하기
        friendRequest = friendRequestRepository.save(friendRequest);

        //return "성공했습니다"
        return FriendResponseDto.toDto(friendRequest);
    }

    @Override
    public FriendResponseDto replyFriend(Long id, Boolean isAccepted) {
        //요청된 친구추가요청 객체 가져오기
        FriendRequest targetRequest = friendRequestRepository.findById(id).orElseThrow();

        //내 UUID 및 이메일 세션에서 가져오기
        String userEmail = authService.getSessionEmail(httpSession);
        //이메일로 아이디 가져오기
        User user = userRepository.findUserByEmail(userEmail).orElseThrow();
        Long userId = user.getId();

        if (!targetRequest.getReceived().equals(userId)) {
            throw new CustomException.UnauthorizedException("본인의 친구 추가 요청만 응답할 수 있습니다.");
        }

        //state 추출하기
        request_state state = request_state.of(isAccepted ? 3 : 2);
        Long friendId = targetRequest.getRequested();

        //state 확인 및 상태 수정
        if (state == request_state.REJECTED) {
            targetRequest.setState(request_state.REJECTED);
            friendRequestRepository.save(targetRequest);
        } else if (state == request_state.ACCEPTED) {
            targetRequest.setState(request_state.ACCEPTED);
            friendRequestRepository.save(targetRequest);
        } else {
            throw new CustomException.BadRequestException("Invalid request state");
        }
        return FriendResponseDto.toDto(targetRequest);
    }

    @Override
    public List<FriendResponseDto> getRequestFriends() {
        String userEmail = authService.getSessionEmail(httpSession);
        User user = userRepository.findUserByEmail(userEmail).orElseThrow();
        Long userId = user.getId();

        List<FriendRequest> friends = friendRequestRepository.findByUser(userId);

        return friends.stream()
                .filter(friendRequest -> friendRequest.getState() == request_state.REQUESTED)
                .map(FriendResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getAllFriends() {
        String userEmail = authService.getSessionEmail(httpSession);
        User user = userRepository.findUserByEmail(userEmail).orElseThrow();
        Long userId = user.getId();

        List<FriendRequest> friends = friendRequestRepository.findByUser(userId);
        return friends.stream()
                .filter(friendRequest -> isValidRequest(request_state.ACCEPTED, friendRequest, userId))
                .map(FriendRequest::getId)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFriend(FriendRequestDto friendRequestDto) {
        User friend = userRepository.findUserByEmail(friendRequestDto.getEmail()).orElseThrow();
        Long friendId = friend.getId();

        String userEmail = authService.getSessionEmail(httpSession);
        User user = userRepository.findUserByEmail(userEmail).orElseThrow();
        Long userId = user.getId();

        List<FriendRequest> existRequests = friendRequestRepository.findExistRequest(friendId, userId);
        FriendRequest targetRequest = existRequests.stream()
                .filter(entity ->
                        entity.getState() == request_state.ACCEPTED
                ).findFirst()
                .orElse(null);

        if (targetRequest == null) {
            throw new CustomException.BadRequestException("해당 요청은 존재하지 않는 친구요청입니다.");
        }

        targetRequest.setState(request_state.REJECTED);
        friendRequestRepository.save(targetRequest);
    }


    private boolean isValidRequest(request_state state, FriendRequest friendRequest, Long userId) {
        return friendRequest.getState() == state && (friendRequest.getRequested().equals(userId) || friendRequest.getReceived().equals(userId));
    }

    private boolean isDuplicated(FriendRequest request) {
        List<FriendRequest> duplicated = friendRequestRepository.findExistRequest(request.getRequested(), request.getReceived());
        return duplicated.stream()
                .anyMatch(entity ->
                        entity.getState() == request_state.REQUESTED
                );
    }
}