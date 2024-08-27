package org.project.portfolio.domain.users.exception;

import org.project.portfolio.global.exception.PortfolioException;
import org.project.portfolio.global.exception.domainErrorCode.UserErrorCode;

public class UserDuplicateEmailException extends PortfolioException {
    public UserDuplicateEmailException() {
        super(UserErrorCode.DUPLICATE_EMAIL);
    }
}
