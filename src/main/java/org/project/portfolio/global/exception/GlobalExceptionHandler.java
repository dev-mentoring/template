package org.project.portfolio.global.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.project.portfolio.global.exception.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    private static final String LOG_FORMAT = "Status: {}, Class: {}, Code: {}, Message: {}";

    @ExceptionHandler(ChaekBilRyoZoException.class)
    public ResponseEntity<ApiErrorResponse> handlerPortfolioException(HttpServletRequest request, ChaekBilRyoZoException e) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.getStatus();
        String exception = e.getClass().getSimpleName();
        String code = errorCode.getCode();
        String message = errorCode.getMessage();

        log.error(LOG_FORMAT, status, exception, code, message);

        return ResponseEntity
                .status(status)
                .body(ApiErrorResponse.of(
                        status,
                        request.getServletPath(),
                        exception,
                        code,
                        errorCode
                ));
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ApiErrorResponse> handlerJsonProcessingException(
            HttpServletRequest request,
            JsonProcessingException e
    ) {
       String exception =  e.getClass().getSimpleName();
       String code = "JSON_PROCESSING_ERROR";
       String message = e.getOriginalMessage();

       log.error(LOG_FORMAT, HttpStatus.BAD_REQUEST, exception, code, message);

       return ResponseEntity
               .status(HttpStatus.BAD_REQUEST)
               .body(ApiErrorResponse.of(
                       HttpStatus.BAD_REQUEST,
                       request.getServletPath(),
                       exception,
                       code,
                       new ErrorCode() {
                           @Override
                           public HttpStatus getStatus() {
                               return HttpStatus.BAD_REQUEST;
                           }

                           @Override
                           public String getCode() {
                               return code;
                           }

                           @Override
                           public String getMessage() {
                               return message;
                           }
                       }
               ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(
            HttpServletRequest request,
            MethodArgumentNotValidException e
    ) {
        String exception = e.getClass().getSimpleName();
        String code = "VALIDATION_Error";
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        log.error(LOG_FORMAT, HttpStatus.BAD_REQUEST, exception, code, message);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResponse.of(
                        HttpStatus.BAD_REQUEST,
                        request.getServletPath(),
                        exception,
                        code,
                        new ErrorCode() {
                            @Override
                            public HttpStatus getStatus() {
                                return HttpStatus.BAD_REQUEST;
                            }

                            @Override
                            public String getCode() {
                                return code;
                            }

                            @Override
                            public String getMessage() {
                                return message;
                            }
                        }
                ));
    }
}
