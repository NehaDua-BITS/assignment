package com.intuit.profilevalidationsystem.service;

import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import com.intuit.profilevalidationsystem.model.Subscription;

import java.util.List;
import java.util.UUID;

public interface ValidationManager {

    void consumeRequest(String requestMsg);

    void consumeResponse(String responseMsg);

    List<Subscription> getSubscriptions(UUID profileId, UpdateEvent event);

    void requestValidationFromProducts(UUID profileId, UpdateEvent event, List<Subscription> subscriptions);

}
