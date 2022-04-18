package com.intuit.profilevalidationsystem.exceptions.types;

import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;

public class InvalidInputException extends ValidationSystemException {

    public InvalidInputException(ErrorCodes errorCodes, Object... args) {
        super(errorCodes, args);
    }
}
