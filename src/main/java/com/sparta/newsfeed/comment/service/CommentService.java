package com.sparta.newsfeed.comment.service;

import com.sparta.newsfeed.Page;
import com.sparta.newsfeed.PageQuery;
import com.sparta.newsfeed.SessionUserProvider;
import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import com.sparta.newsfeed.comment.dto.CommentResponseDto;
import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.exception.CustomException;
import com.sparta.newsfeed.newsfeed.entity.Newsfeed;
import com.sparta.newsfeed.newsfeed.repository.NewsfeedRepository;
import com.sparta.newsfeed.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final NewsfeedRepository newsfeedRepository;
    private final CommentRepository commentRepository;

    private final SessionUserProvider sessionUserProvider;


    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        User user = sessionUserProvider.getAuthenticatedUser();
        Newsfeed newsfeed = newsfeedRepository.findById(requestDto.newsfeedId());

        Comment comment = new Comment(requestDto.contents(), user, newsfeed);
        comment = commentRepository.save(comment);

        return CommentResponseDto.toDto(comment);
    }

    public Page<CommentResponseDto> findAllComments(PageQuery page, Long newsfeedId) {
        var comments = commentRepository.findAllByNewsfeedId(page.toPageable(), newsfeedId);

        return Page.from(comments
                .map(CommentResponseDto::toDto));
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto) {
        User user = sessionUserProvider.getAuthenticatedUser();
        Comment foundComment = commentRepository
                .findById(commentId)
                .orElseThrow(() ->
                        new CustomException.TargetNotFoundException("요청하신 댓글이 존재하지 않습니다.")
                );

        if(!foundComment.getUser().getId().equals(user.getId()))
            throw new CustomException.UnauthorizedException("댓글 수정은 댓글 작성자만 가능합니다.");

        foundComment.partialUpdate(requestDto);
        foundComment = commentRepository.save(foundComment);

        return CommentResponseDto.toDto(foundComment);
    }

    public void deleteComment(Long commentId) {
        Comment foundComment = commentRepository
                .findById(commentId)
                .orElseThrow(() ->
                        new CustomException.TargetNotFoundException("요청하신 댓글이 존재하지 않습니다.")
                );

        Long currentUserId = sessionUserProvider.getAuthenticatedUser().getId();
        Long commentWriterId = foundComment.getUser().getId();
        Long newsfeedWriterId = foundComment.getNewsfeed().getUser().getId();

        if(!currentUserId.equals(commentWriterId)
                && !currentUserId.equals(newsfeedWriterId)
        ) throw new CustomException.UnauthorizedException("해당 댓글은 피드 작성자 혹은 댓글 작성자만 삭제할 수 있습니다.");

        commentRepository.deleteById(commentId);
    }
}
