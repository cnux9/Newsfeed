package com.sparta.newsfeed.friend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long requested;
    Long received;
    request_state state;

    public Friend(Long requested, Long received, request_state state) {
        this.requested = requested;
        this.received = received;
        this.state = state;
    }

    public Friend() {}
}
