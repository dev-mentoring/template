package org.project.portfolio.common

/** 비즈니스 예외 */
class BusinessException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message) {
}
