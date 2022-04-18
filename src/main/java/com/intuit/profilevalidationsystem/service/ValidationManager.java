package com.intuit.profilevalidationsystem.service;

import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import com.intuit.profilevalidationsystem.model.Subscription;

import java.util.List;
import java.util.UUID;

public interface ValidationManager {

    void processUpdateRequest(String requestMsg) throws Exception;

    void requestValidationFromProducts(UpdateEvent event, List<Subscription> subscriptions) throws Exception;

}
