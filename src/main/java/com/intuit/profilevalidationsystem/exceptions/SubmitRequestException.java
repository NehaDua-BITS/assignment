package com.intuit.profilevalidationsystem.exceptions;

import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;
import lombok.Getter;

@Getter
public class SubmitRequestException extends ValidationSystemException {

    public SubmitRequestException(Integer statusCode, String code, String message) {
        super(statusCode, code, message);
    }

    public SubmitRequestException(String code, String message) {
        super(code, message);
    }

    public SubmitRequestException(ErrorCodes errorCodes, Object... args) {
        super(errorCodes, args);
    }

}
