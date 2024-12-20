package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.cofig.PasswordEncoder;
import com.sparta.newsfeed.user.dto.SignUpResponseDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
