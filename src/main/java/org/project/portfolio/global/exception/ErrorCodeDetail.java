package org.project.portfolio.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorCodeDetail {
    private final String exception;     // 에러 클래스명
    private final String code;          // 에러 코드명
    private final String message;       // 에러 메시지 ErrorCode.message

    public static ErrorCodeDetail of(String exception, String code, ErrorCode errorCode) {
        return new ErrorCodeDetail(exception, code, errorCode.getMessage());
    }

    public static ErrorCodeDetail from(ErrorCode errorCode) {
        return new ErrorCodeDetail(
                "UnknownException",
                errorCode.getCode(),
                errorCode.getMessage()
        );
    }
}
