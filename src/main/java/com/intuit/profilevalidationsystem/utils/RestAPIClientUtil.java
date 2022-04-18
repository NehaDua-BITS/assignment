package com.intuit.profilevalidationsystem.utils;

import com.intuit.profilevalidationsystem.dto.ValidationResponseDTO;
import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;
import com.intuit.profilevalidationsystem.exceptions.types.ProductServiceException;
import com.intuit.profilevalidationsystem.exceptions.types.ValidationSystemException;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@EnableRetry
@Component
public class RestAPIClientUtil
{
    @Autowired
    @Qualifier("resttemp")
    private RestTemplate restTemplate;

    @Retryable(value = ProductServiceException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public ValidationResponseDTO apiCall(String url, HttpMethod httpMethod, UpdateEvent event, String product, Map<String, String> uriVariablesMap)
    {
        ValidationResponseDTO responseDTO = null;
        ResponseEntity<ValidationResponseDTO> responseEntity = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UpdateEvent> entity = new HttpEntity<>(event, httpHeaders);
        try {
            responseEntity = restTemplate.exchange(url, httpMethod, entity, ValidationResponseDTO.class,
                    uriVariablesMap == null ? Collections.emptyMap() : uriVariablesMap);
            responseDTO = responseEntity.getBody();
            log.info("Product {} Response = {}", product, responseDTO.getValidationResponse());
            if (responseDTO.getError() != null) {
                log.error("Product {} returned error : {}", product, responseDTO.getError());
            }
        } catch (HttpServerErrorException | ResourceAccessException ex) {  //retrying only if server or connection error
            log.error("Error in Api Call. Retrying for : url={} ; error={}" , url, ex.getMessage(), ex);
            throw new ProductServiceException(ErrorCodes.VALIDATION_FAILURE, product, ex.getMessage());
        } catch (Exception ex) {
            log.error("Product API call failed : url={}; error={}", url, ex.getMessage(), ex);
            throw new ValidationSystemException(ErrorCodes.EXTERNAL_API_CALL_FAILED, ex.getMessage());
        }

        return responseDTO;
    }

}
