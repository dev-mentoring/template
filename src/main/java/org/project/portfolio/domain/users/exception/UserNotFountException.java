package org.project.portfolio.domain.users.exception;

import org.project.portfolio.global.exception.PortfolioException;
import org.project.portfolio.global.exception.domainErrorCode.UserErrorCode;

public class UserNotFountException extends PortfolioException {
    public UserNotFountException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
