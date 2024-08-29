package org.project.portfolio.global.security.jwt.exception;

import org.project.portfolio.global.exception.PortfolioException;
import org.project.portfolio.global.exception.domainErrorCode.LoginErrorCode;

public class RefreshTokenNotFoundException extends PortfolioException {
    public RefreshTokenNotFoundException() {
        super(LoginErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
