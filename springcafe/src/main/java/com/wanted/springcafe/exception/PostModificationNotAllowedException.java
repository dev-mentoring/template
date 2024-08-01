package com.wanted.springcafe.exception;

public class PostModificationNotAllowedException extends RuntimeException{

    public PostModificationNotAllowedException() {}

    public PostModificationNotAllowedException(String message) {
        super(message);
    }

    public PostModificationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
