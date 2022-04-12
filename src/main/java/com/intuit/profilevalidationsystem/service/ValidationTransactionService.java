package com.intuit.profilevalidationsystem.service;

import com.intuit.profilevalidationsystem.constants.ProductType;
import com.intuit.profilevalidationsystem.constants.Status;
import com.intuit.profilevalidationsystem.constants.ValidationStatus;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;

import java.util.UUID;

public interface ValidationTransactionService {

    void addUpdateTransactionEntry(UUID profileId, UpdateEvent event);

    void addValidationTransactionEntry(UUID updateTxId, UpdateEvent event, ProductType productType);

    void updateTransactionEntry(UUID updateId, ValidationStatus validationStatus);

    void updateValidationTransactionEntry(UUID updateTxId, ProductType productType, ValidationStatus validationStatus);

}
