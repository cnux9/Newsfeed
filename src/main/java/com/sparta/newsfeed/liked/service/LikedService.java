package com.sparta.newsfeed.liked.service;

import com.sparta.newsfeed.auth.service.AuthService;
import com.sparta.newsfeed.exception.CustomException;
import com.sparta.newsfeed.liked.dto.LikedResponseDto;
import com.sparta.newsfeed.newsfeed.entity.Newsfeed;
import com.sparta.newsfeed.newsfeed.repository.NewsfeedRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class LikedService {
    private final NewsfeedRepository newsfeedRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final HttpSession session;

    public LikedResponseDto UpdateForNewsfeedLiked(Long id){
        User user = getAuthenticatedUser();
        Newsfeed targetNewsfeed = newsfeedRepository.findById(id);
        if(targetNewsfeed.getUser().getId().equals(user.getId()))
            throw new CustomException.BadRequestException("자추는 불가능합니다.");

        Set<User> likedUsers = targetNewsfeed.getLikedUsers();
        if(!likedUsers.contains(user))
            targetNewsfeed.addLiked(user);
        else
            targetNewsfeed.removeLiked(user);

        targetNewsfeed = newsfeedRepository.save(targetNewsfeed);

        return new LikedResponseDto(
                targetNewsfeed.getId(),
                likedUsers.size());
    }

    /*
    public int UpdateForCommentLiked(Long id){
        User user = getAuthenticatedUser();
        Newsfeed targetNewsfeed = newsfeedRepository.findById(id);

        List<User> likedUsers = targetNewsfeed.getLikedUsers();
        if(likedUsers.contains(user))
            targetNewsfeed.addLiked(user);
        else
            targetNewsfeed.removeLiked(user);

        return likedUsers.size();
    }
     */

    private User getAuthenticatedUser() {
        User user = userRepository.findUserByEmail(authService.getSessionEmail(session)).orElse(null);
        if (user == null) {
            throw new CustomException.UnauthorizedException("유효한 세션이 아닙니다!");
        }
        return user;
    }
}
