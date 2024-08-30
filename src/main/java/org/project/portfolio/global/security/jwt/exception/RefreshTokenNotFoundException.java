package org.project.portfolio.global.security.jwt.exception;

import org.project.portfolio.global.exception.ChaekBilRyoZoException;
import org.project.portfolio.global.exception.domainErrorCode.LoginErrorCode;

public class RefreshTokenNotFoundException extends ChaekBilRyoZoException {
    public RefreshTokenNotFoundException() {
        super(LoginErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
