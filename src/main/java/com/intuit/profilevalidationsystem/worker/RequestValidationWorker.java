package com.intuit.profilevalidationsystem.worker;

import com.intuit.profilevalidationsystem.config.ProductAPIConfig;
import com.intuit.profilevalidationsystem.constants.ProductType;
import com.intuit.profilevalidationsystem.constants.UpdateStatus;
import com.intuit.profilevalidationsystem.constants.ValidationResponse;
import com.intuit.profilevalidationsystem.dto.ValidationResponseDTO;
import com.intuit.profilevalidationsystem.exceptions.types.ProductServiceException;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import com.intuit.profilevalidationsystem.utils.RestAPIClientUtil;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class RequestValidationWorker implements Callable<UpdateStatus> {

    private RestAPIClientUtil restAPIClientUtil;

    private ProductAPIConfig productAPIConfig;

    private ProductType productType;

    private UpdateEvent event;

    public RequestValidationWorker(RestAPIClientUtil restAPIClientUtil, ProductAPIConfig productAPIConfig, ProductType productType, UpdateEvent event) {
        this.restAPIClientUtil = restAPIClientUtil;
        this.productAPIConfig = productAPIConfig;
        this.productType = productType;
        this.event = event;
    }

    @Override
    public UpdateStatus call() {
        UpdateStatus updateStatus;
        try {
            Map<String, String> uriVariablesMap = new HashMap<>();
            uriVariablesMap.put("profileId", event.getUserId());
            uriVariablesMap.put("productId", productType.toString());
            ValidationResponseDTO validationResponseDTO = restAPIClientUtil.apiCall(productAPIConfig.getUrl(), HttpMethod.POST,
                    event, productType.toString(), uriVariablesMap);
            if (validationResponseDTO.getError() == null) {
                updateStatus = mapValidationResponsToUpdateStatus(validationResponseDTO);
            } else {
                updateStatus = UpdateStatus.FAILED;
            }
        } catch (ProductServiceException ex) {
            updateStatus = UpdateStatus.TIMEOUT;
        }
        return updateStatus;
    }

    private UpdateStatus mapValidationResponsToUpdateStatus(ValidationResponseDTO validationResponseDTO) {
        ValidationResponse response = validationResponseDTO.getValidationResponse();
        return response.equals(ValidationResponse.ACCEPTED) ? UpdateStatus.ACCEPTED : UpdateStatus.REJECTED;
    }

}
