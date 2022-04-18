package com.intuit.profilevalidationsystem.exceptions.types;

import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;

public class RepositoryException extends ValidationSystemException {
    public RepositoryException(String code, String message) {
        super(code, message);
    }

    public RepositoryException(Integer statusCode, String code, String message) {
        super(statusCode, code, message);
    }

    public RepositoryException(Integer statusCode, String code, String message, Throwable cause) {
        super(statusCode, code, message, cause);
    }

    public RepositoryException(ErrorCodes errorCodes, Object... args) {
        super(errorCodes, args);
    }

    public RepositoryException(String message) {
        super(message);
    }
}
