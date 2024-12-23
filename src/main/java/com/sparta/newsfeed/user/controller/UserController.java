package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.user.dto.SignUpRequestDto;
import com.sparta.newsfeed.user.dto.SignUpResponseDto;
import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<SignUpResponseDto> createUser(
            @Validated @RequestBody SignUpRequestDto requestDto
    ) {
        SignUpResponseDto responseDto = userService.createUser(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.findUser(id);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto requestDto,
            HttpSession session
    ) {
        UserResponseDto responseDto =
                userService.updateUser(
                        id,
                        requestDto,
                        session
                );
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    /*
    사용자를 삭제하기 위해 DeleteMapping을 사용하였다.
    애초에 로그인 필터가 있기 때문에 사용자 수정과 삭제에서는 일단 비밀번호 검증을 하지 않았다.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, HttpSession session) {
        userService.deleteUser(id, session);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
