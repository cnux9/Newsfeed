package com.sparta.newsfeed.comment.service;

import com.sparta.newsfeed.auth.service.AuthService;
import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import com.sparta.newsfeed.comment.dto.CommentResponseDto;
import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.exception.CustomException;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

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

        Page<Comment> page = commentRespository.findAllByNewsfeedId(newsfeedId, pageRequest);
        List<Comment> commentResponseDtoList = page.getContent();
        return commentResponseDtoList
                .stream()
                .map(CommentResponseDto::new)
                .toList();
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, HttpSession session) {

        Comment foundComment = commentRespository.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User commentWriter = userRepository.findById(foundComment.getUser().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String sessionEmail = authService.getSessionEmail(session);

        if (!sessionEmail.equals(commentWriter.getEmail())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        foundComment.setContents(requestDto.getContents());

        return new CommentResponseDto(foundComment);
    }

    public void deleteComment(Long commentId, HttpSession session) {
        Comment foundComment = commentRespository.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User commentWriter = userRepository.findById(foundComment.getUser().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User newsfeedWriter = userRepository.findById(foundComment.getNewsfeed().getUser().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String sessionEmail = authService.getSessionEmail(session);

        if (!sessionEmail.equals(commentWriter.getEmail()) && !sessionEmail.equals(newsfeedWriter.getEmail())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        commentRespository.delete(foundComment);
    }
}
