package com.intuit.profilevalidationsystem.kafka;

import com.intuit.profilevalidationsystem.constants.ProductType;
import com.intuit.profilevalidationsystem.constants.ValidationResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateValidationResponseEvent {

    private String profileId;

    private UpdateEvent event;

    private ProductType sender;

    private ValidationResponse response;

}
