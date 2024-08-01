package com.wanted.springcafe.exception;

public class NotCommentAuthorException extends RuntimeException{

    public NotCommentAuthorException() {
    }

    public NotCommentAuthorException(String message) {
        super(message);
    }

    public NotCommentAuthorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotCommentAuthorException(Throwable cause) {
        super(cause);
    }
}
