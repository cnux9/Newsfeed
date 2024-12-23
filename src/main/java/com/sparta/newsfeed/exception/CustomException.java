package com.sparta.newsfeed.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final String errorCode;
    private final int httpStatus;

    public CustomException(String message, String errorCode, int httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public static class TargetNotFoundException extends CustomException {
        public TargetNotFoundException(String message) {
            super(message, "NOT_FOUND", 404);
        }
    }

    public static class UnauthorizedException extends CustomException {
        public UnauthorizedException(String message) {
            super(message, "UNAUTHORIZED", 401);
        }
    }
}