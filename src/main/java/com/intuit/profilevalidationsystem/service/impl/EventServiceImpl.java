package com.intuit.profilevalidationsystem.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intuit.profilevalidationsystem.constants.EventType;
import com.intuit.profilevalidationsystem.dto.ProfileDTO;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.intuit.profilevalidationsystem.constants.Properties.REQUEST_ID;

@Service
public class EventServiceImpl {

    public UpdateEvent prepareEvent(UUID profileId, ProfileDTO profileDTO, String source) throws JsonProcessingException {
        UpdateEvent updateEvent = new UpdateEvent(profileId.toString(), profileDTO, EventType.UPDATE_PROFILE);
        updateEvent.setSource(source);
        updateEvent.setRequestId(MDC.get(REQUEST_ID));
        return updateEvent;
    }

}
