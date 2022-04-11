package com.intuit.profilevalidationsystem.kafka;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.intuit.profilevalidationsystem.constants.EventType;
import com.intuit.profilevalidationsystem.dto.ProfileDTO;
import com.intuit.profilevalidationsystem.utils.ZonedDateDeserializer;
import com.intuit.profilevalidationsystem.utils.ZonedDateSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateEvent {

    private String userId;

    private ProfileDTO profileDTO;

    private EventType eventType;

    @JsonSerialize(using = ZonedDateSerializer.class)
    @JsonDeserialize(using = ZonedDateDeserializer.class)
    private ZonedDateTime updateTime;

    private String source;

    private String requestId;

    public UpdateEvent(String userId, ProfileDTO profileDTO, EventType eventType) {
        this.userId = userId;
        this.profileDTO = profileDTO;
        this.eventType = eventType;
        this.updateTime = ZonedDateTime.now();
    }

}
