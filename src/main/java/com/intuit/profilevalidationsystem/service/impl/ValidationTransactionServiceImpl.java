package com.intuit.profilevalidationsystem.service.impl;

import com.intuit.profilevalidationsystem.constants.ProductType;
import com.intuit.profilevalidationsystem.constants.ValidationStatus;
import com.intuit.profilevalidationsystem.dao.UpdateTransactionRepository;
import com.intuit.profilevalidationsystem.dao.ValidationTransactionRepository;
import com.intuit.profilevalidationsystem.exceptions.RepositoryException;
import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import com.intuit.profilevalidationsystem.model.UpdateTransaction;
import com.intuit.profilevalidationsystem.model.UpdateValidationTransaction;
import com.intuit.profilevalidationsystem.service.ValidationTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
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
    public void addUpdateTransactionEntry(UUID profileId, UpdateEvent event) {

        UpdateTransaction updateTransaction = new UpdateTransaction();
        updateTransaction.setProfileId(profileId);
        updateTransaction.setUpdateTransactionId(UUID.fromString(event.getRequestId()));
        updateTransaction.setEvent(event);
        updateTransaction.setStatus(ValidationStatus.IN_PROGRESS);
        updateTransaction.setCreateTimestamp(Date.from(event.getUpdateTime().toInstant()));

        //save in DB
        updateTransactionRepository.save(updateTransaction);
        log.info("UpdateTxId={} profileId = {} saved in transaction DB", event.getRequestId(), profileId);
    }

    @Override
    public void addValidationTransactionEntry(UUID updateTxId, UpdateEvent event, ProductType productType) {
        UpdateValidationTransaction updateValidationTransaction = new UpdateValidationTransaction();
        updateValidationTransaction.setUpdateTransactionId(updateTxId);
        updateValidationTransaction.setSubTransactionId(UUID.randomUUID());
        updateValidationTransaction.setProduct(productType);
        updateValidationTransaction.setStatus(ValidationStatus.IN_PROGRESS);
        updateValidationTransaction.setRequestTimestamp(Date.from(event.getUpdateTime().toInstant()));

        //save in DB
        validationTransactionRepository.save(updateValidationTransaction);
        log.info("ValidationTxId={} updateId = {} saved in validation transaction DB", updateTxId, event.getRequestId());
    }

    @Override
    public void updateTransactionEntry(UUID updateId, ValidationStatus validationStatus) {
        Optional<UpdateTransaction> txOptional = updateTransactionRepository.findById(updateId);
        if (!txOptional.isPresent()) {
            throw new RepositoryException(ErrorCodes.UPDATE_TX_NOT_FOUND);
        }
        UpdateTransaction updateTransaction = txOptional.get();
        updateTransaction.setStatus(validationStatus);

        //update in db
        updateTransactionRepository.save(updateTransaction);
    }

    @Override
    public void updateValidationTransactionEntry(UUID updateTxId, ProductType productType, ValidationStatus validationStatus) {
        UpdateValidationTransaction transaction = validationTransactionRepository.findByUpdateTransactionIdAndProduct(updateTxId, productType);
        if (transaction == null) {
            throw new RepositoryException(ErrorCodes.VALIDATION_TX_NOT_FOUND);
        }
        transaction.setStatus(validationStatus);
        transaction.setResponseTimestamp(Date.from(ZonedDateTime.now().toInstant()));

        //update validationStatus in db
        validationTransactionRepository.save(transaction);
    }
}
