package org.project.portfolio.common

import org.springframework.http.HttpStatus

/** 에러 코드 */
enum class ErrorCode(
    val httpStatus: HttpStatus,
    val code: String,
    val message: String
) {
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, "C001", "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "C002", "만료된 토큰입니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "유효하지 않은 사용자입니다."),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "U002", "이미 존재하는 사용자입니다."),
    USER_INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "U003", "유효하지 않은 비밀번호입니다."),
    USER_INVALID_EMAIL(HttpStatus.BAD_REQUEST, "U004", "유효하지 않은 이메일입니다."),
    USER_INVALID_PHONE(HttpStatus.BAD_REQUEST, "U005", "유효하지 않은 전화번호입니다."),
    USER_INVALID_NAME(HttpStatus.BAD_REQUEST, "U006", "유효하지 않은 이름입니다."),

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "V001", "입력값이 올바르지 않습니다."),
    ;
}
