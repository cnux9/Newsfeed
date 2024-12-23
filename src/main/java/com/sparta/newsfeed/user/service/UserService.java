package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.auth.Auth;
import com.sparta.newsfeed.config.PasswordEncoder;
import com.sparta.newsfeed.user.dto.SignUpRequestDto;
import com.sparta.newsfeed.user.dto.SignUpResponseDto;
import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponseDto createUser(SignUpRequestDto dto) {
        User user = new User(dto.getName(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
        User savedUser;
        try {
            savedUser = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 이메일입니다.");
        }

        return new SignUpResponseDto(savedUser.getName(), savedUser.getEmail());
    }

    public UserResponseDto findUserById(Long id) {
        User foundUser = userRepository.findByIdOrElseThrow(id);
        return new UserResponseDto(id, foundUser.getEmail(), foundUser.getName());
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto){
        User foundUser = userRepository.findByIdOrElseThrow(id);
        foundUser.toUserResponseDto(requestDto);

        return new UserResponseDto(id, requestDto.getEmail(), requestDto.getName());
    }


    public void deleteUser(Long id, HttpSession session){
        User foundUser = userRepository.findByIdOrElseThrow(id);

        Auth.logout(session);
        userRepository.delete(foundUser);
    }

}
