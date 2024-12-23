package com.sparta.newsfeed.auth.service;

import com.sparta.newsfeed.config.PasswordEncoder;
import com.sparta.newsfeed.auth.dto.AuthRequestDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    /*
    직관성을 위해 LoginService -> AuthService로 이름변경
    가입된 사용자 정보를 불러오기 위해 UserRepository 사용
    사용자의 해싱된 비밀번호값과 평문비밀번호를 비교하기 위해 PasswordEncoder 사용
     */
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Map<UUID, String> sessionMap = new HashMap<>();

    public void login(AuthRequestDto requestDto, HttpSession session) {
        User user = userRepository.findUserByEmailOrElseThrow(requestDto.getEmail());

        if (user.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist email" + requestDto.getEmail());
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 다릅니다.");
        }

        UUID uuid = UUID.randomUUID();
        session.setAttribute("sessionKey", uuid);
        sessionMap.put(uuid, user.getEmail());
    }

    public void logout(HttpSession session) {
        UUID uuid = (UUID) session.getAttribute("sessionKey");
        sessionMap.remove(uuid);

        session.invalidate();
    }
}
