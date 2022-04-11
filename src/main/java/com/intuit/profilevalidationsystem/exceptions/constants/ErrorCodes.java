package com.intuit.profilevalidationsystem.exceptions.constants;

import lombok.Getter;

@Getter
public enum ErrorCodes {

    //400 series
    INVALID_INPUT(400, "SM.4001", "Invalid Input : %s"),
    OPERATION_NOT_SUPPORTED(400, "SM.4002", "Unsupported operation"),

    //500 series
    SUBMIT_UPDATE_REQUEST_FAILED(500, "SM.5001", "Failed to submit update profile request : %s");

    private final String code;
    private final String message;
    private int status;

    ErrorCodes(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
