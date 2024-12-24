package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.user.dto.*;
import com.sparta.newsfeed.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserResponseDto> createUser(
            @Validated @RequestBody SignUpRequestDto requestDto
    ) {
        UserResponseDto responseDto = userService.createUser(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.findUser(id);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(
            @Valid @RequestBody UserUpdateRequestDto requestDto,
            HttpSession session
    ) {
        UserResponseDto responseDto =
                userService.updateUser(
                        requestDto,
                        session
                );
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
            @Valid @RequestBody UserDeleteRequestDto requestDto , HttpSession session
    ) {
        userService.deleteUser(requestDto, session);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
