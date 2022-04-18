package com.intuit.profilevalidationsystem.exceptions.types;

import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;

public class ProductServiceException extends ValidationSystemException {
    public ProductServiceException(ErrorCodes errorCode, Object... args) {
        super(errorCode, args);
    }
}
