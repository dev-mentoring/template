package org.project.portfolio.global.security.jwt.exception;

import org.project.portfolio.global.exception.ErrorCode;
import org.project.portfolio.global.exception.ChaekBilRyoZoException;
import org.project.portfolio.global.exception.domainErrorCode.LoginErrorCode;

public class JwtExpiredException extends ChaekBilRyoZoException {

    public JwtExpiredException(ErrorCode errorCode) {
        super(LoginErrorCode.JWT_EXPIRED);
    }
}

