package com.intuit.profilevalidationsystem.exceptions.types;

import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;
import lombok.Getter;

@Getter
public class ValidationSystemException extends RuntimeException {

    protected Integer httpStatus;
    protected String code;
    protected String message;

    public ValidationSystemException(String code, String message) {
        this(null, code, message);
    }

    public ValidationSystemException(Integer statusCode, String code, String message) {
        this(statusCode, code, message, null);
    }

    public ValidationSystemException(Integer statusCode, String code, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = statusCode;
        this.code = code;
        this.message = message;
    }

    public ValidationSystemException(ErrorCodes errorCodes, Object... args) {
        this(errorCodes.getStatus(), errorCodes.getCode(), String.format(errorCodes.getMessage(), args));
    }

    public ValidationSystemException(String message) {
        this(500, "500", message);
    }
}
