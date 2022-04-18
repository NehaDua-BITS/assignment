package com.intuit.profilevalidationsystem.service;

import com.intuit.profilevalidationsystem.model.UpdateTransaction;

import java.util.UUID;

public interface StatusService {

    UpdateTransaction getUpdateTransaction(UUID transactionId);
}
