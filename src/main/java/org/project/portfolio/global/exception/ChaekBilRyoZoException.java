package org.project.portfolio.global.exception;

import lombok.Getter;

@Getter
public class ChaekBilRyoZoException extends RuntimeException {
    private final ErrorCode errorCode;

    public ChaekBilRyoZoException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
