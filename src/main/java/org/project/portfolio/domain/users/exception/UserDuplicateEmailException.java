package org.project.portfolio.domain.users.exception;

import org.project.portfolio.global.exception.ChaekBilRyoZoException;
import org.project.portfolio.global.exception.domainErrorCode.UserErrorCode;

public class UserDuplicateEmailException extends ChaekBilRyoZoException {
    public UserDuplicateEmailException() {
        super(UserErrorCode.DUPLICATE_EMAIL);
    }
}
