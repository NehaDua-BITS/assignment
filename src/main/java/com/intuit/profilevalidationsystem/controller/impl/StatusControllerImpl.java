package com.intuit.profilevalidationsystem.controller.impl;

import com.intuit.profilevalidationsystem.controller.StatusController;
import com.intuit.profilevalidationsystem.dto.response.UpdateStatusDTO;
import com.intuit.profilevalidationsystem.model.UpdateTransaction;
import com.intuit.profilevalidationsystem.service.impl.StatusServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/request")
@Slf4j
public class StatusControllerImpl implements StatusController {

    private StatusServiceImpl statusService;

    @Autowired
    public StatusControllerImpl(StatusServiceImpl statusService) {
        this.statusService = statusService;
    }

    @Override
    public ResponseEntity<UpdateStatusDTO> getUpdateStatus(UUID requestId) {
        UpdateTransaction transaction = statusService.getUpdateTransaction(requestId);
        UpdateStatusDTO responseDTO = new UpdateStatusDTO(requestId, transaction.getStatus(), transaction.getCreateTimestamp());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
