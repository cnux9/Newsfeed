package com.sparta.newsfeed.friend.repository;

import com.sparta.newsfeed.friend.entity.Friend;
import com.sparta.newsfeed.friend.entity.request_state;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByState(request_state state);

    @Query("SELECT f FROM Friend f WHERE f.requested = :userId OR f.received = :userId")
    List<Friend> findByUser(@Param("userId") Long userId);
}
