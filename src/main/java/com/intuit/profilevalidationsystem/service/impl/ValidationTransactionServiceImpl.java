package com.intuit.profilevalidationsystem.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intuit.profilevalidationsystem.constants.UpdateStatus;
import com.intuit.profilevalidationsystem.dao.UpdateTransactionRepository;
import com.intuit.profilevalidationsystem.dao.ValidationTransactionRepository;
import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;
import com.intuit.profilevalidationsystem.exceptions.types.RepositoryException;
import com.intuit.profilevalidationsystem.helper.Mapper;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import com.intuit.profilevalidationsystem.model.UpdateTransaction;
import com.intuit.profilevalidationsystem.service.ValidationTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ValidationTransactionServiceImpl implements ValidationTransactionService {

    private UpdateTransactionRepository updateTransactionRepository;

    private ValidationTransactionRepository validationTransactionRepository;

    @Autowired
    public ValidationTransactionServiceImpl(UpdateTransactionRepository updateTransactionRepository, ValidationTransactionRepository validationTransactionRepository) {
        this.updateTransactionRepository = updateTransactionRepository;
        this.validationTransactionRepository = validationTransactionRepository;
    }

    @Override
    public void addUpdateTransactionEntry(UUID profileId, UpdateEvent event) throws JsonProcessingException {

        UpdateTransaction updateTransaction = new UpdateTransaction();
        updateTransaction.setProfileId(profileId);
        updateTransaction.setUpdateTransactionId(UUID.fromString(event.getRequestId()));
        updateTransaction.setStatus(UpdateStatus.IN_PROGRESS);
        updateTransaction.setRequestTimestamp(Date.from(event.getUpdateTime().toInstant()));
        updateTransaction.setCreateTimestamp(Date.from(Instant.now()));
        updateTransaction.setLastUpdatedTimestamp(Date.from(Instant.now()));

        //save in DB
        updateTransactionRepository.save(updateTransaction);
        log.info("UpdateTxId={} profileId = {} saved in transaction DB", event.getRequestId(), profileId);
    }

    @Override
    public UpdateTransaction updateTransactionEntry(UUID updateId, UpdateStatus updateStatus) {
        Optional<UpdateTransaction> txOptional = updateTransactionRepository.findById(updateId);
        if (!txOptional.isPresent()) {
            throw new RepositoryException(ErrorCodes.UPDATE_TX_NOT_FOUND);
        }
        UpdateTransaction updateTransaction = txOptional.get();
        updateTransaction.setStatus(updateStatus);
        updateTransaction.setLastUpdatedTimestamp(Date.from(Instant.now()));

        //update in db
        return updateTransactionRepository.save(updateTransaction);
    }

}
