package com.sparta.newsfeed.friend.repository;

import com.sparta.newsfeed.friend.entity.FriendRequest;
import com.sparta.newsfeed.friend.entity.request_state;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByState(request_state state);

    @Query("SELECT f FROM FriendRequest f WHERE f.requested = :userId OR f.received = :userId")
    List<FriendRequest> findByUser(@Param("userId") Long userId);


    @Query("SELECT fr FROM FriendRequest fr WHERE " +
            "((fr.requested = :requested AND fr.received = :received) " +
            "OR (fr.requested = :received AND fr.received = :requested)) ")
    List<FriendRequest> findExistRequest(@Param("requested") Long requested,
                                             @Param("received") Long received);

    FriendRequest findByReceivedAndRequested(Long requested, Long received);
}
