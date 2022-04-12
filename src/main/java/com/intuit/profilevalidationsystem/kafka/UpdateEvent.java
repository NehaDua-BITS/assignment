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

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateEvent extends Event {

    @NotNull(message = "user id cannot be null")
    private String userId;

    @NotNull(message = "Profile DTO cannot be null")
    private ProfileDTO profileDTO;

    @JsonSerialize(using = ZonedDateSerializer.class)
    @JsonDeserialize(using = ZonedDateDeserializer.class)
    private ZonedDateTime updateTime;

    @NotNull(message = "Update source cannot be null")
    private String source;

    public UpdateEvent(String userId, ProfileDTO profileDTO, EventType eventType) {
        super(eventType);
        this.userId = userId;
        this.profileDTO = profileDTO;
        this.eventType = eventType;
        this.updateTime = ZonedDateTime.now();
    }

}
