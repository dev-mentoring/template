package org.project.portfolio.global.security.jwt.exception;

import org.project.portfolio.global.exception.PortfolioException;
import org.project.portfolio.global.exception.domainErrorCode.LoginErrorCode;

public class UserNotFoundException extends PortfolioException {
    public UserNotFoundException() {
        super(LoginErrorCode.USER_NOT_FOUND);
    }
}