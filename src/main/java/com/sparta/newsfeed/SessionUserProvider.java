package com.sparta.newsfeed;

import com.sparta.newsfeed.auth.service.AuthService;
import com.sparta.newsfeed.exception.CustomException;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionUserProvider {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final HttpSession session;

    public User getAuthenticatedUser() {
        User user = userRepository.findUserByEmail(authService.getSessionEmail(session)).orElse(null);
        if (user == null) {
            throw new CustomException.UnauthorizedException("유효한 세션이 아닙니다!");
        }
        return user;
    }
}
