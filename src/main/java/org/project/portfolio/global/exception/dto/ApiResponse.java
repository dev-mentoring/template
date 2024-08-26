package org.project.portfolio.global.exception.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse {
    private final Boolean success;      // 요청 성공 여부
    private final HttpStatus status;    // HTTP 상태 코드
    private final String path;          // 요청 경로

    // ApiResponse 객체 생성하고 반환
    public static ApiResponse of(Boolean success, HttpStatus status, String path) {
        return new ApiResponse(success, status, path);
    }
}
