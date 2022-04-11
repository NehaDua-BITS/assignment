package com.intuit.profilevalidationsystem.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Consumer {

    @KafkaListener(topics = {"#{'${kafka.consumer.topic}'}"})
    public void consume(String message) {
        log.info(String.format("#### -> Consumed message -> %s", message));
    }
}