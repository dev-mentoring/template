package org.project.portfolio.global.exception;

import lombok.Getter;

@Getter
public class PortfolioException extends RuntimeException {
    private final ErrorCode errorCode;

    public PortfolioException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
