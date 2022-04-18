package com.intuit.profilevalidationsystem.kafka;

import com.intuit.profilevalidationsystem.constants.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.MDC;

import java.util.UUID;

import static com.intuit.profilevalidationsystem.constants.Properties.REQUEST_ID;

@NoArgsConstructor
@Getter
@Setter
public class Event {

    protected EventType eventType;

    protected String requestId;

}
