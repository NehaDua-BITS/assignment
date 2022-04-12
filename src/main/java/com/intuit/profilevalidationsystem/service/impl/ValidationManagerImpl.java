package com.intuit.profilevalidationsystem.service.impl;

import com.intuit.profilevalidationsystem.exceptions.InvalidInputException;
import com.intuit.profilevalidationsystem.exceptions.ValidationSystemException;
import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;
import com.intuit.profilevalidationsystem.helper.Mapper;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import com.intuit.profilevalidationsystem.service.ValidationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class ValidationManagerImpl implements ValidationManager {

    @Override
    public void validate(String eventString) {
        if (eventString == null || StringUtils.isEmpty(eventString)) {
            throw new InvalidInputException(ErrorCodes.EMPTY_EVENT);
        }

        UpdateEvent event = null;
        try {
            event = Mapper.fromJson(eventString, UpdateEvent.class);
        } catch (IOException e) {
            log.error("Failed to convert update event to json : ", e.getMessage());
            throw new ValidationSystemException(ErrorCodes.INVALID_EVENT, e.getMessage());
        }

        //extract user id
        UUID userId = UUID.fromString(event.getUserId());

        //fetch product subscriptions from DB and call validate api
    }
}
