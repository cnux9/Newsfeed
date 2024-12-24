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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        // 비밀번호 패턴 검증
        validatePassword(requestDto.getPassword());

        // 이메일 중복 검사 + 재가입 불가능 검사
        if (userRepository.existsUserByEmail(requestDto.getEmail())) {
            throw new CustomException.EmailUnavailableException();
        }

        User savedUser = userRepository.save(new User(
                requestDto.getName(),
                requestDto.getEmail(),
                passwordEncoder.encode(requestDto.getPassword())
        ));

        return new UserResponseDto(savedUser);
    }

    public UserResponseDto findUser(Long id) {
        User foundUser = findUserByIdOrElseThrow(id);
        return new UserResponseDto(foundUser);
    }

    public UserResponseDto updateUser(UserUpdateRequestDto requestDto, HttpSession session){
        validatePassword(requestDto.getNewPassword());

        // 같은 비밀번호로 변경하려는 경우
        if (requestDto.getOldPassword().equals(requestDto.getNewPassword())) {
            throw new CustomException.UpdateWithSamePasswordException();
        }

        User foundUser = findUserBySessionOrElseThrow(session);
        passwordsMatchOrElseThrow(requestDto.getOldPassword(), foundUser.getPassword());

        foundUser.partialUpdate(
                requestDto.getName(),
                passwordEncoder.encode(requestDto.getNewPassword())
        );

        return new UserResponseDto(foundUser);
    }

    public void deleteUser(UserDeleteRequestDto requestDto , HttpSession session){
        User foundUser = findUserBySessionOrElseThrow(session);
        passwordsMatchOrElseThrow(requestDto.getPassword(), foundUser.getPassword());

        newsfeedRepository.deleteNewsfeedsByUserId(foundUser.getId());
        foundUser.updateSoftDelete();

        authService.logout(session);
    }

    private User findUserByIdOrElseThrow(Long id) {
        return userRepository.findById(id).orElseThrow(CustomException.NoSuchUserException::new);
    }

    private User findUserBySessionOrElseThrow(HttpSession session) {
        String sessionEmail = authService.getSessionEmail(session);
        User user = findUserByEmailOrElseThrow(sessionEmail);

        return user;
    }

    private User findUserByEmailOrElseThrow(String sessionEmail) {
        return userRepository.findUserByEmail(sessionEmail).orElseThrow(CustomException.NoSuchUserException::new);
    }

    private void passwordsMatchOrElseThrow(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new CustomException.WrongPasswordException();
        }
    }

    public void validatePassword(String password) {
        Matcher matcher = passwordPattern.matcher(password);
        if (!matcher.matches()) {
            throw new CustomException.InvalidPasswordException();
        }
    }

}
