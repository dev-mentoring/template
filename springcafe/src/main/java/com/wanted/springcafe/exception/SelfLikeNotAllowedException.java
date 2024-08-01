package com.wanted.springcafe.exception;

public class SelfLikeNotAllowedException extends RuntimeException{
    public SelfLikeNotAllowedException() {}

    public SelfLikeNotAllowedException(String message) {
        super(message);
    }

    public SelfLikeNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SelfLikeNotAllowedException(Throwable cause) {
        super(cause);
    }
}
