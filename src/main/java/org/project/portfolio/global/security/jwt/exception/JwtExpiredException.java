package org.project.portfolio.global.security.jwt.exception;

import org.project.portfolio.global.exception.ErrorCode;
import org.project.portfolio.global.exception.PortfolioException;
import org.project.portfolio.global.exception.domainErrorCode.LoginErrorCode;

public class JwtExpiredException extends PortfolioException {

    public JwtExpiredException(ErrorCode errorCode) {
        super(LoginErrorCode.JWT_EXPIRED);
    }
}

