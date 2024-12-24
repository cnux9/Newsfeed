package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.auth.service.AuthService;
import com.sparta.newsfeed.config.PasswordEncoder;
import com.sparta.newsfeed.exception.CustomException;
import com.sparta.newsfeed.newsfeed.repository.NewsfeedRepository;
import com.sparta.newsfeed.user.dto.*;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    // TODO: 메소드에 선언? 필드에 선언?
    private final AuthService authService;
    private final NewsfeedRepository newsfeedRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$");

    public UserResponseDto createUser(SignUpRequestDto requestDto) {
        validatePassword(requestDto.getPassword());

        if (userRepository.existsUserByEmail(requestDto.getEmail())) {
            // 해당 이메일의 사용자가 탈퇴했는지 여부는 알 수 없음
            throw new CustomException.EmailUnavailableException();
        }
        User user = new User(requestDto.getName(), requestDto.getEmail(), passwordEncoder.encode(requestDto.getPassword()));
        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    public UserResponseDto findUser(Long id) {
        User foundUser = userRepository.findById(id).orElseThrow(CustomException.NoSuchUserException::new);
        return new UserResponseDto(id, foundUser.getEmail(), foundUser.getName());
    }

    public UserResponseDto updateUser(Long id, UserUpdateRequestDto requestDto, HttpSession session){
        validatePassword(requestDto.getNewPassword());

        User foundUser = userRepository.findById(id).orElseThrow(CustomException.NoSuchUserException::new);

        String sessionEmail = authService.getSessionEmail(session);

        if (!foundUser.getEmail().equals(sessionEmail)) {
            throw new CustomException.UnauthorizedUserUpdateException();
        }

        if (requestDto.getOldPassword().equals(requestDto.getNewPassword())) {
            throw new CustomException.UpdateWithSamePasswordException();
        }

        if (!passwordEncoder.matches(requestDto.getOldPassword(), foundUser.getPassword())) {
            throw new CustomException.WrongPasswordException();
        }

        foundUser.partialUpdate(
                requestDto.getName(),
                passwordEncoder.encode(requestDto.getNewPassword())
        );

        return new UserResponseDto(id, foundUser.getEmail(), foundUser.getName());
    }

    public void deleteUser(Long id, UserDeleteRequestDto requestDto , HttpSession session){
        User foundUser = userRepository.findById(id).orElseThrow(CustomException.NoSuchUserException::new);

        //TODO:겹치는 코드 , 메서드 추출 ?
        if (!passwordEncoder.matches(requestDto.getPassword(), foundUser.getPassword())) {
            throw new CustomException.WrongPasswordException();
        }

        newsfeedRepository.deleteNewsfeedsByUserId(id);
        //TODO: 에러 처리
        foundUser.updateSoftDelete();

        authService.logout(session);
    }

    public void validatePassword(String password) {
        Matcher matcher = passwordPattern.matcher(password);
        if (!matcher.matches()) {
            throw new CustomException.InvalidPasswordException();
        }
    }

}
