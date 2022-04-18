package com.intuit.profilevalidationsystem.service;

import com.intuit.profilevalidationsystem.model.Subscription;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {

    List<Subscription> getSubscriptionsByProfileId(UUID profileId);
}
