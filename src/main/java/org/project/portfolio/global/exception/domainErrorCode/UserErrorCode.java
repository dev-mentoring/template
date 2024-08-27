package org.project.portfolio.global.exception.domainErrorCode;

import lombok.RequiredArgsConstructor;
import org.project.portfolio.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "중복된 사용자가 존재합니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복된 이메일이 존재합니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
