package com.sparta.newsfeed.friend.entity;

public enum request_state {
    NONE(0), REQUESTED(1), REJECTED(2), ACCEPTED(3);

    request_state(int i) {
    }

    public static request_state of(int i) {
        switch (i) {
            case 1:
                return REQUESTED;
            case 2:
                return REJECTED;
            case 3:
                return ACCEPTED;
            default:
                return NONE;
        }
    }
}
