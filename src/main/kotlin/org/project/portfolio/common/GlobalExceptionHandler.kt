package org.project.portfolio.common

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


/** RestAPI 전역 예외 처리 */
@RestControllerAdvice
class GlobalExceptionHandler {

    /** 비즈니스 예외 처리 */
    @ExceptionHandler(BusinessException::class)
    private fun handleBusinessException(businessException: BusinessException): ResponseEntity<ApiResponse> {

        return ResponseEntity
            .status(
                businessException.errorCode.httpStatus
            )
            .body(
                ApiResponse(
                    resultCode = businessException.errorCode.code,
                    resultMessage = businessException.errorCode.message
                )
            )
    }

    /** 입력값 오류 처리 */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    private fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse> {
        val resultMessage = e.bindingResult.fieldErrors.joinToString(", ") {
            "${it.field} ${it.defaultMessage}"
        }
        return ResponseEntity
            .status(
                ErrorCode.INVALID_INPUT_VALUE.httpStatus
            )
            .body(
                ApiResponse(
                    resultCode = ErrorCode.INVALID_INPUT_VALUE.code,
                    resultMessage = "${ErrorCode.INVALID_INPUT_VALUE.message} $resultMessage"
                )
            )
    }

    /** API 응답 */
    inner class ApiResponse(
        val resultCode: String,
        val resultMessage: String
    ) {
        override fun toString(): String {
            return "ApiResponse(resultCode='$resultCode', resultMessage='$resultMessage')"
        }
    }
}



