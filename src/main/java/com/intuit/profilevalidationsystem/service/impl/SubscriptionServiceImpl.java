package com.intuit.profilevalidationsystem.service.impl;

import com.intuit.profilevalidationsystem.dao.SubscriptionRepository;
import com.intuit.profilevalidationsystem.model.Subscription;
import com.intuit.profilevalidationsystem.service.SubscriptionService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Slf4j
@Service
@NoArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public List<Subscription> getSubscriptionsByProfileId(UUID profileId) {
        return subscriptionRepository.findByProfileId(profileId);
    }
}
