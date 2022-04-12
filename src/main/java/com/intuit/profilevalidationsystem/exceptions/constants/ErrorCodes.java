package com.intuit.profilevalidationsystem.exceptions.constants;

import lombok.Getter;

@Getter
public enum ErrorCodes {

    //400 series
    INVALID_INPUT(400, "PV.4001", "Invalid Input : %s"),
    OPERATION_NOT_SUPPORTED(400, "PV.4002", "Unsupported operation"),
    INVALID_SUBSCRIPTION_FREQUENCY(400, "PV.4003", "Subscription frequency not supported"),


    //500 series
    SUBMIT_UPDATE_REQUEST_FAILED(500, "PV.5001", "Failed to submit update profile request : %s"),
    INVALID_EVENT(500, "PV.5002", "Failed to convert event to json or vice-versa : %s"),
    EVENT_TYPE_NULL(500, "PV.5003", "Event type cannot be null"),
    EMPTY_EVENT(500, "PV.5004", "Event cannot be null");


    private final String code;
    private final String message;
    private int status;

    ErrorCodes(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
