package com.intuit.profilevalidationsystem.service.impl;

import com.intuit.profilevalidationsystem.config.ProductAPIConfig;
import com.intuit.profilevalidationsystem.constants.ProductType;
import com.intuit.profilevalidationsystem.constants.UpdateStatus;
import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;
import com.intuit.profilevalidationsystem.exceptions.types.InvalidInputException;
import com.intuit.profilevalidationsystem.exceptions.types.SubscriptionException;
import com.intuit.profilevalidationsystem.exceptions.types.ValidationSystemException;
import com.intuit.profilevalidationsystem.helper.Mapper;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import com.intuit.profilevalidationsystem.model.Subscription;
import com.intuit.profilevalidationsystem.service.SubscriptionService;
import com.intuit.profilevalidationsystem.service.ValidationManager;
import com.intuit.profilevalidationsystem.utils.RestAPIClientUtil;
import com.intuit.profilevalidationsystem.worker.RequestValidationWorker;
import com.intuit.profilevalidationsystem.worker.UpdateWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.FutureTask;

@Service
@Slf4j
public class ValidationManagerImpl implements ValidationManager {

    @Autowired
    private RestAPIClientUtil restAPIClientUtil;

    @Autowired
    private ProductAPIConfig productAPIConfig;

    @Autowired
    private ValidationTransactionServiceImpl validationTransactionService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UpdateWorker updateWorker;

    @Override
    public void processUpdateRequest(String requestMsg) throws Exception {
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
        List<Subscription> subscriptions = subscriptionService.getSubscriptionsByProfileId(profileId);
        log.info("Subscriptions are : " + subscriptions);
        if (subscriptions == null || subscriptions.size() == 0) {
            throw new SubscriptionException(ErrorCodes.NO_SUBSCRIPTIONS_FOUND);
        }

        //add tx entry in DB
        validationTransactionService.addUpdateTransactionEntry(profileId, event);

        log.info("Sending request to products for update validation !!");
        requestValidationFromProducts(event, subscriptions);
    }

    @Override
    public void requestValidationFromProducts(UpdateEvent event, List<Subscription> subscriptions) throws Exception {
        int length = subscriptions.size();
        FutureTask[] futureTasks = new FutureTask[length];
        UpdateStatus[] statuses = new UpdateStatus[length];
        for (int i = 0; i < length; i++) {
            ProductType productType = subscriptions.get(i).getProductType();
            RequestValidationWorker worker = new RequestValidationWorker(restAPIClientUtil, productAPIConfig, productType, event);
            futureTasks[i] = new FutureTask(worker);
            Thread t = new Thread(futureTasks[i]);
            t.start();
        }

        log.info("Added all validation future tasks");

        for (int i = 0; i < length; i++) {
            statuses[i] = (UpdateStatus) futureTasks[i].get();
        }

        updateWorker.updateOverallValidationStatus(event, statuses, event.getProfileDTO());
    }

}
