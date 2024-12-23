package com.sparta.newsfeed.auth;

import jakarta.servlet.http.HttpSession;

public interface Auth {

    static void login(HttpSession session, String email) {
        session.setAttribute("sessionKey", email);
    }

    static void logout(HttpSession session) {
        session.invalidate();
    }
}

