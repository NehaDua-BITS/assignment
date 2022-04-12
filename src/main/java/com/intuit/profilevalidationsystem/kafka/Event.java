package com.intuit.profilevalidationsystem.kafka;

import com.intuit.profilevalidationsystem.constants.EventType;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.MDC;

import javax.validation.constraints.NotNull;

import static com.intuit.profilevalidationsystem.constants.Properties.REQUEST_ID;

@Getter
@Setter
public class Event {

    @NotNull(message = "Event type cannot be null")
    protected EventType eventType;

    protected String requestId;

    public Event(EventType eventType) {
        this.eventType = eventType;
        this.requestId = MDC.get(REQUEST_ID);
    }
}
