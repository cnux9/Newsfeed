package com.sparta.newsfeed.friend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long requested;

    @Column(nullable = false)
    Long received;

    @Setter
    @Column(nullable = false)
    request_state state;

    public FriendRequest(Long requested, Long received, request_state state) {
        this.requested = requested;
        this.received = received;
        this.state = state;
    }

    public FriendRequest() {}
}
