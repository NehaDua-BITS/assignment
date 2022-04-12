package com.intuit.profilevalidationsystem.exceptions;

import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;

public class InvalidInputException extends ValidationSystemException {

    public InvalidInputException(ErrorCodes errorCodes, Object... args) {
        super(errorCodes, args);
    }
}
