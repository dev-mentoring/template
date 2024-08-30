package org.project.portfolio.domain.users.exception;

import org.project.portfolio.global.exception.ChaekBilRyoZoException;
import org.project.portfolio.global.exception.domainErrorCode.UserErrorCode;

public class UserDuplicateUsernameException extends ChaekBilRyoZoException {
    public UserDuplicateUsernameException() {
        super(UserErrorCode.DUPLICATE_USERNAME);
    }
}
