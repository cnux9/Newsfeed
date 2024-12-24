package com.sparta.newsfeed.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

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

    public static class NoSuchUserException extends CustomException {
        public NoSuchUserException() {
            super("Does not exist such user.", "NOT_FOUND", 404);
        }
    }

    public static class InvalidPasswordException extends CustomException {
        public InvalidPasswordException() {
            super("Password is invalid.", "BAD_REQUEST", 400);
        }
    }

    public static class WrongPasswordException extends CustomException {
        public WrongPasswordException() {
            super("Password is wrong.", "BAD_REQUEST", 400);
        }
    }

    public static class EmailUnavailableException extends CustomException {
        public EmailUnavailableException() {
            super("Email is unavailable", "BAD_REQUEST", 400);
        }
    }

    public static class UnauthorizedUserUpdateException extends CustomException {
        public UnauthorizedUserUpdateException() {
            super("You can not update other user's profile.", "UNAUTHORIZED", 401);
        }
    }

    public static class UpdateWithSamePasswordException extends CustomException {
        public UpdateWithSamePasswordException() {
            super("You can not change with same password.", "BAD_REQUEST", 400);
        }
    }

}