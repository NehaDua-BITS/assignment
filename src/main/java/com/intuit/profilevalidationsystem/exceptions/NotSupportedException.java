package com.intuit.profilevalidationsystem.exceptions;

import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;

public class NotSupportedException extends ValidationSystemException {

    public NotSupportedException(String message) {
        super(ErrorCodes.OPERATION_NOT_SUPPORTED);
    }
}
