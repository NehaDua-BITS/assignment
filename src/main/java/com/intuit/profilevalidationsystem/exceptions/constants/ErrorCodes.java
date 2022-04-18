package com.intuit.profilevalidationsystem.exceptions.constants;

import lombok.Getter;

@Getter
public enum ErrorCodes {

    //400 series
    INVALID_INPUT(400, "PV.4001", "Invalid Input : %s"),
    OPERATION_NOT_SUPPORTED(400, "PV.4002", "Unsupported operation"),
    INVALID_SUBSCRIPTION_FREQUENCY(400, "PV.4003", "Subscription frequency not supported"),
    ADDRESS_NULL(400, "PV.4004", "Address is null"),
    NO_SUBSCRIPTIONS_FOUND(400, "PV.4005", "No subscriptions found for given profile"),
    NO_PROFILE_FOUND(400, "PV.4006", "No profile found for given id"),
    NO_REQUEST_FOUND(400, "PV.4007", "No request found with given id"),


    //500 series
    SUBMIT_UPDATE_REQUEST_FAILED(500, "PV.5001", "Failed to submit update profile request : %s"),
    INVALID_EVENT(500, "PV.5002", "Failed to convert event to json or vice-versa : %s"),
    EVENT_TYPE_NULL(500, "PV.5003", "Event type cannot be null"),
    EMPTY_REQUEST_EVENT(500, "PV.5004", "Request event cannot be null"),
    EMPTY_RESPONSE_EVENT(500, "PV.5005", "Response event is null"),


    //600 series for DB related
    VALIDATION_TX_NOT_FOUND(500, "PV.6001", "Validation transaction not found in db for given params"),
    UPDATE_TX_NOT_FOUND(500, "PV.6002", "Update transaction not found in db for given id"),

    //700 series for 3rd party failures
    VALIDATION_FAILURE(500, "PV.7001", "Validation failed for Product = %s due to Error = %s"),
    EXTERNAL_API_CALL_FAILED(500, "PV.7002", "External API call failed : %s");



    private final String code;
    private final String message;
    private int status;

    ErrorCodes(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
