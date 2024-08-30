package org.project.portfolio.global.security.jwt.exception;

import org.project.portfolio.global.exception.ChaekBilRyoZoException;
import org.project.portfolio.global.exception.domainErrorCode.LoginErrorCode;

public class UserNotFoundException extends ChaekBilRyoZoException {
    public UserNotFoundException() {
        super(LoginErrorCode.USER_NOT_FOUND);
    }
}