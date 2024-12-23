package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.auth.service.AuthService;
import com.sparta.newsfeed.config.PasswordEncoder;
import com.sparta.newsfeed.newsfeed.repository.NewsfeedRepository;
import com.sparta.newsfeed.user.dto.SignUpRequestDto;
import com.sparta.newsfeed.user.dto.SignUpResponseDto;
import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    // TODO: 메소드에 선언? 필드에 선언?
    private final AuthService authService;
    private final NewsfeedRepository newsfeedRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponseDto createUser(SignUpRequestDto dto) {
        if (userRepository.existsUserByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "가입이 불가능한 이메일입니다.");
        }
        User user = new User(dto.getName(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
        User savedUser = userRepository.save(user);

        return new SignUpResponseDto(savedUser.getName(), savedUser.getEmail());
    }

    public UserResponseDto findUser(Long id) {
        User foundUser = userRepository.findByIdOrElseThrow(id);
        return new UserResponseDto(id, foundUser.getEmail(), foundUser.getName());
    }

    public UserResponseDto updateUser(Long id, UserRequestDto requestDto){
        User foundUser = userRepository.findByIdOrElseThrow(id);
        foundUser.toUserResponseDto(requestDto);

        return new UserResponseDto(id, requestDto.getEmail(), requestDto.getName());
    }


    public void deleteUser(Long id, HttpSession session){
//        TODO: deleteNewsfeedsByUserId 메소드 구현
//        newsfeedRepository.deleteNewsfeedsByUserId(id);

        User foundUser = userRepository.findByIdOrElseThrow(id);
//        TODO: deleteNewsfeedsByUserId 메소드 구현
        foundUser.updateSoftDelete();

        authService.logout(session);
    }

}
