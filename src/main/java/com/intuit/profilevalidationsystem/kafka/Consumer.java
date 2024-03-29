package com.intuit.profilevalidationsystem.kafka;

import com.intuit.profilevalidationsystem.service.ValidationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Consumer {

    private ValidationManager validationService;

    @Autowired
    public Consumer(ValidationManager validationService) {
        this.validationService = validationService;
    }

    @KafkaListener(topics = {"#{'${kafka.consumer.topic.request}'}"})
    public void consumeUpdateRequest(String message) throws Exception {
        log.info(String.format("#### -> Consumed request message -> %s", message));
        validationService.processUpdateRequest(message);
    }

}