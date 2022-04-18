package com.intuit.profilevalidationsystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intuit.profilevalidationsystem.constants.UpdateStatus;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import com.intuit.profilevalidationsystem.model.UpdateTransaction;

import java.util.UUID;

public interface ValidationTransactionService {

    void addUpdateTransactionEntry(UUID profileId, UpdateEvent event) throws JsonProcessingException;

    UpdateTransaction updateTransactionEntry(UUID updateId, UpdateStatus validationStatus);

}
