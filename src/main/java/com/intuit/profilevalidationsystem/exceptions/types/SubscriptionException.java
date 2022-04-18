package com.intuit.profilevalidationsystem.exceptions.types;

import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;

public class SubscriptionException extends ValidationSystemException {

    public SubscriptionException(String code, String message) {
        super(code, message);
    }

    public SubscriptionException(Integer statusCode, String code, String message) {
        super(statusCode, code, message);
    }

    public SubscriptionException(Integer statusCode, String code, String message, Throwable cause) {
        super(statusCode, code, message, cause);
    }

    public SubscriptionException(ErrorCodes errorCodes, Object... args) {
        super(errorCodes, args);
    }
}
