package com.intuit.profilevalidationsystem.service.impl;

import com.intuit.profilevalidationsystem.config.ProductAPIConfig;
import com.intuit.profilevalidationsystem.constants.ProductType;
import com.intuit.profilevalidationsystem.constants.ResponseType;
import com.intuit.profilevalidationsystem.constants.ValidationStatus;
import com.intuit.profilevalidationsystem.dao.SubscriptionRepository;
import com.intuit.profilevalidationsystem.exceptions.InvalidInputException;
import com.intuit.profilevalidationsystem.exceptions.SubscriptionException;
import com.intuit.profilevalidationsystem.exceptions.ValidationSystemException;
import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;
import com.intuit.profilevalidationsystem.helper.Mapper;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import com.intuit.profilevalidationsystem.kafka.UpdateValidationResponseEvent;
import com.intuit.profilevalidationsystem.model.Subscription;
import com.intuit.profilevalidationsystem.service.ValidationManager;
import com.intuit.profilevalidationsystem.utils.RestAPIClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ValidationManagerImpl implements ValidationManager {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private RestAPIClientUtil restAPIClientUtil;

    @Autowired
    private ProductAPIConfig productAPIConfig;

    @Autowired
    private ValidationTransactionServiceImpl validationTransactionService;

    @Override
    public void consumeRequest(String requestMsg) {
        if (requestMsg == null || StringUtils.isEmpty(requestMsg)) {
            throw new InvalidInputException(ErrorCodes.EMPTY_REQUEST_EVENT);
        }

        UpdateEvent event = null;
        try {
            event = Mapper.fromJson(requestMsg, UpdateEvent.class);
        } catch (IOException e) {
            log.error("Failed to convert update event to json : ", e.getMessage());
            throw new ValidationSystemException(ErrorCodes.INVALID_EVENT, e.getMessage());
        }

        //extract user id
        UUID profileId = UUID.fromString(event.getUserId());

        //fetch product subscriptions from DB and call validate api
        List<Subscription> subscriptions = getSubscriptions(profileId, event);
        log.info("Subscriptions are : " + subscriptions);
        if (subscriptions == null || subscriptions.size() == 0) {
            throw new SubscriptionException(ErrorCodes.NO_SUBSCRIPTIONS_FOUND);
        }

        //add tx entry in DB
        validationTransactionService.addUpdateTransactionEntry(profileId, event);

        log.info("Sending request to products for update validation !!");
        requestValidationFromProducts(profileId, event, subscriptions);
    }

    @Override
    public void consumeResponse(String responseMsg) {
        try {
            UpdateValidationResponseEvent responseEvent = Mapper.fromJson(responseMsg, UpdateValidationResponseEvent.class);
            ValidationStatus status = null;
            if (responseEvent.getResponse() ==  ResponseType.ACCEPTED) {
                status = ValidationStatus.ACCEPTED;
            } else if (responseEvent.getResponse() == ResponseType.REJECTED) {
                status = ValidationStatus.REJECTED;
            } else {
                throw new ValidationSystemException("Invalid validation response received : " + responseEvent);
            }

            validationTransactionService.updateValidationTransactionEntry(UUID.fromString(responseEvent.getEvent().getRequestId()),
                    responseEvent.getSender(), status);

        } catch (IOException e) {
            throw new ValidationSystemException("Failed to map validation response: " + e.getMessage());
        }
    }

    @Override
    public List<Subscription> getSubscriptions(UUID profileId, UpdateEvent event) {
        return subscriptionRepository.findByProfileId(profileId);
    }

    @Override
    public void requestValidationFromProducts(UUID profileId, UpdateEvent event, List<Subscription> subscriptions) {
        int length = subscriptions.size();
        for (int i = 0; i < length; i++) {
            ProductType productType = subscriptions.get(i).getProductType();
            restAPIClientUtil.apiCall(productAPIConfig.getUrl(), HttpMethod.POST,
                    event, productType.toString());
            validationTransactionService.addValidationTransactionEntry(UUID.fromString(event.getRequestId()), event, productType);
        }
    }
}
