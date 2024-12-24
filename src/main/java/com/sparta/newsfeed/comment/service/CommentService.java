package com.sparta.newsfeed.comment.service;

import com.sparta.newsfeed.auth.service.AuthService;
import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import com.sparta.newsfeed.comment.dto.CommentResponseDto;
import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.newsfeed.entity.Newsfeed;
import com.sparta.newsfeed.newsfeed.repository.NewsfeedRepository;
import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final UserRepository userRepository;
    private final NewsfeedRepository newsfeedRepository;
    private final CommentRepository commentRespository;

    private final AuthService authService;

    private final int PAGE_NUMBER = 10;
    private final int PAGE_SIZE = 10;


    public CommentResponseDto createComment(CommentRequestDto requestDto, HttpSession session) {
        String sessionEmail = authService.getSessionEmail(session);
        User foundUser = userRepository.findUserByEmail(sessionEmail).orElseThrow();

        Newsfeed newsfeed = newsfeedRepository.findById(requestDto.getNewsfeedId());

        Comment comment = new Comment(requestDto.getContents());
        comment.setUserAndNewsfeed(foundUser, newsfeed);

        commentRespository.save(comment);

        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> findComments(Long newsfeedId) {
        PageRequest pageRequest = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);

        Page<Comment> page = commentRespository.findAllByTaskId(newsfeedId, pageRequest);
        List<Comment> commentResponseDtoList = page.getContent();
        return commentResponseDtoList
                .stream()
                .map(CommentResponseDto::new)
                .toList();
    }

    public UserResponseDto updateComment(Long id, UserRequestDto requestDto, HttpSession session) {
        return null;
    }

    public void deleteComment(Long id, HttpSession session) {


    }
}
