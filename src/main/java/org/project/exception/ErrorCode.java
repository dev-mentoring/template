package org.project.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    MEMBER_EMAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "M001", "이미 사용중인 이메일입니다.");

    private final HttpStatus status;

    private final String code;

    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
