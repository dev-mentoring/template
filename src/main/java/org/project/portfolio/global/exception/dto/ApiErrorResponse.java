package org.project.portfolio.global.exception.dto;

import lombok.Getter;
import org.project.portfolio.global.exception.ErrorCode;
import org.project.portfolio.global.exception.ErrorCodeDetail;
import org.springframework.http.HttpStatus;

@Getter
public class ApiErrorResponse extends ApiResponse {
    private final ErrorCodeDetail error;

    private ApiErrorResponse(
            HttpStatus status,
            String path,
            String exception,
            String code,
            ErrorCode errorCode
    ) {
        super(false, status, path);
        this.error = ErrorCodeDetail.of(exception, code, errorCode);
    }

    // ApiErrorResponse 객체 생성 후 반환
    public static ApiErrorResponse of(
            HttpStatus status,
            String path,
            String exception,
            String code,
            ErrorCode errorCode
    ) {
        return new ApiErrorResponse(status, path, exception, code, errorCode);
    }
}