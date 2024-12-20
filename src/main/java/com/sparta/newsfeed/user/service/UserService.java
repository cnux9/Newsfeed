package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.config.PasswordEncoder;
import com.sparta.newsfeed.user.dto.SignUpResponseDto;
import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponseDto signUp(String username, String email, String password) {
        User user = new User(username, email, passwordEncoder.encode(password));
        User saveUser = userRepository.save(user);

        return new SignUpResponseDto(saveUser.getName(),saveUser.getEmail());
    }

    public UserResponseDto findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) { //예외처리를 위한 조건문
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        User findUser = optionalUser.get();
        return new UserResponseDto(id, findUser.getEmail(), findUser.getName());
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto){
        User findUser = userRepository.findByIdOrElseThrow(id);
        findUser.toUserResponseDto(requestDto);

        return new UserResponseDto(id, requestDto.getEmail(), requestDto.getName());
    }


    public void delete(Long id){
        User findMember = userRepository.findByIdOrElseThrow(id);
        userRepository.delete(findMember);
    }

}
