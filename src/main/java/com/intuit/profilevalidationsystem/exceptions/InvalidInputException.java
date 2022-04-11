package com.intuit.profilevalidationsystem.exceptions;

import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;

public class InvalidInputException extends ValidationSystemException {
    public InvalidInputException(ErrorCodes errorCodes, String message) {
        super(errorCodes, message);
    }
}
