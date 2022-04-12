package com.intuit.profilevalidationsystem.utils;

import com.intuit.profilevalidationsystem.exceptions.ValidationSystemException;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@EnableRetry
@Component
public class RestAPIClientUtil
{
    @Autowired
    @Qualifier("resttemp")
    private RestTemplate restTemplate;

    @Retryable(value = ValidationSystemException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public void apiCall(String url, HttpMethod httpMethod, UpdateEvent event, String product)
    {
        String apiUrl = String.format("%s/%s", url, product);
        HttpEntity<UpdateEvent> entity = new HttpEntity<>(event);
        try {
            ResponseEntity responseEntity = restTemplate.postForEntity(apiUrl, entity, String.class);
            if (responseEntity.getStatusCode() != HttpStatus.ACCEPTED) {
                log.info("Product {} didn't accept the request !", product);
            }
        } catch (Exception ex) {  //retrying only if server or connection error
            log.error("Error in Api Call : url={} ; message={}" , apiUrl, ex.getMessage(), ex);
            throw new ValidationSystemException(String.format("Validation API Call failed: url=%s ; message=%s", apiUrl, ex.getMessage()));
        }
        log.info("Sent validation api call for : " + product);
    }
}
