package com.intuit.profilevalidationsystem.exceptions.types;

import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;

public class NotSupportedException extends ValidationSystemException {

    public NotSupportedException(ErrorCodes errorCode, Object... args) {
        super(errorCode, args);
    }
}
